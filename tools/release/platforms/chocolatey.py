import logging
import os

import requests

from platforms.common import (
    ReleaseException,
    copy_from_docker_windows,
    docker,
    temp_file_with_contents,
    temp_move_file,
)
from releases import get_version_and_timestamp_from_release


def validate(windows_host, image_tag, nupkg_path):
    """ Spin up a fresh docker image, and make sure that the nupkg installs and runs """
    DOCKERFILE = r"""\
FROM  microsoft/windowsservercore:1803
SHELL ["powershell", "-command"]
ARG version=
ARG timestamp=
ARG repository=facebook/buck

# Install chocolatey
RUN Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))

RUN mkdir c:/choco_temp
WORKDIR c:/choco_temp
ADD {nupkg_filename} c:/choco_temp/{nupkg_filename}
RUN choco install -y -s '.;https://chocolatey.org/api/v2/' buck
RUN Write-Host "" -NoNewLine > .buckconfig
RUN buck --help
"""
    build_dir = os.path.dirname(nupkg_path)
    nupkg_filename = os.path.basename(nupkg_path)
    dockerfile_temp_path = os.path.join(build_dir, "Dockerfile")
    dockerfile = DOCKERFILE.format(nupkg_filename=nupkg_filename)

    with temp_file_with_contents(dockerfile_temp_path, dockerfile):
        docker(
            windows_host,
            ["build", "-m", "2g", "-t", image_tag + "-validate", build_dir],
        )
        docker(windows_host, ["rmi", image_tag + "-validate"])


def build_chocolatey(repository, release, windows_host, output_dir):
    """
    Builds a .nupkg package in docker, and copies it to the host.

    Args:
        repository: The github repository to use. username/repo
        release: The release object from github
        windows_host: If set, the docker host ot use that can run windows containers
                      If not None, this should be a format that would work with
                      docker -H
        output_dir: The directory to place artifacts in after the build

    Returns:
        The path to the artifact
    """
    release_version, release_timestamp = get_version_and_timestamp_from_release(release)
    image_tag = "buck:" + release_version
    nupkg_name = "buck.{}.nupkg".format(release_version)
    nupkg_path = os.path.join(output_dir, nupkg_name)

    # Get the changelog from github rather than locally
    changelog_path = os.path.join(
        "tools", "release", "platforms", "chocolatey", "Changelog.md"
    )
    changelog = release["body"].strip() or "Periodic release"

    with temp_move_file(changelog_path), temp_file_with_contents(
        changelog_path, changelog
    ):
        logging.info("Building windows docker image...")
        docker(
            windows_host,
            [
                "build",
                "-m",
                "2g",  # Default memory is 1G
                "-t",
                image_tag,
                "--build-arg",
                "version=" + release_version,
                "--build-arg",
                "timestamp=" + str(release_timestamp),
                "--build-arg",
                "repository=" + repository,
                "tools/release/platforms/chocolatey",
            ],
        )

    logging.info("Copying nupkg out of docker container")
    copy_from_docker_windows(windows_host, image_tag, "/src/buck.nupkg", nupkg_path)

    logging.info("Validating that .nupkg installs...")
    validate(windows_host, image_tag, nupkg_path)

    logging.info("Built .nupkg file at {}".format(nupkg_path))
    return nupkg_path


def publish_chocolatey(chocolatey_file, chocolatey_api_key):
    """ Publish a nupkg to chocolatey """
    url = "https://chocolatey.org/api/v2/package"
    headers = {"X-NuGet-ApiKey": chocolatey_api_key}

    logging.info("Publishing chocolatey package at {}".format(chocolatey_file))
    with open(chocolatey_file, "rb") as fin:
        response = requests.put(url, headers=headers, data=fin)
    if response.status_code == 409:
        raise ReleaseException("Package and version already exists on chocolatey")
    response.raise_for_status()
    logging.info("Published chocolatey package at {}".format(chocolatey_file))
