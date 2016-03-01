/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.cxx;

import com.facebook.buck.model.BuildTargets;
import com.facebook.buck.rules.AbstractBuildRule;
import com.facebook.buck.rules.AddToRuleKey;
import com.facebook.buck.rules.BuildContext;
import com.facebook.buck.rules.BuildRuleParams;
import com.facebook.buck.rules.BuildableContext;
import com.facebook.buck.rules.RuleKeyAppendable;
import com.facebook.buck.rules.RuleKeyBuilder;
import com.facebook.buck.rules.SourcePath;
import com.facebook.buck.rules.SourcePathResolver;
import com.facebook.buck.rules.args.RuleKeyAppendableFunction;
import com.facebook.buck.rules.coercer.FrameworkPath;
import com.facebook.buck.shell.DefaultShellStep;
import com.facebook.buck.step.Step;
import com.facebook.buck.step.fs.MkdirStep;
import com.facebook.buck.util.MoreIterables;
import com.google.common.base.Functions;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import java.nio.file.Path;

/**
 * Generate the CFG for a source file
 */
public class CxxInferCapture extends AbstractBuildRule implements RuleKeyAppendable {

  @AddToRuleKey
  private final CxxInferTools inferTools;
  private final CxxToolFlags preprocessorFlags;
  private final CxxToolFlags compilerFlags;
  @AddToRuleKey
  private final SourcePath input;
  private final CxxSource.Type inputType;
  @AddToRuleKey(stringify = true)
  private final Path output;
  private final ImmutableSet<Path> includeRoots;
  private final ImmutableSet<Path> systemIncludeRoots;
  private final ImmutableSet<Path> headerMaps;
  @AddToRuleKey
  private final ImmutableSet<FrameworkPath> frameworkRoots;
  @AddToRuleKey
  private final RuleKeyAppendableFunction<FrameworkPath, Path> frameworkPathSearchPathFunction;
  @AddToRuleKey
  private final Optional<SourcePath> prefixHeader;

  private final Path resultsDir;
  private final DebugPathSanitizer sanitizer;

  CxxInferCapture(
      BuildRuleParams buildRuleParams,
      SourcePathResolver pathResolver,
      CxxToolFlags preprocessorFlags,
      CxxToolFlags compilerFlags,
      SourcePath input,
      AbstractCxxSource.Type inputType,
      Path output,
      ImmutableSet<Path> includeRoots,
      ImmutableSet<Path> systemIncludeRoots,
      ImmutableSet<Path> headerMaps,
      ImmutableSet<FrameworkPath> frameworkRoots,
      RuleKeyAppendableFunction<FrameworkPath, Path> frameworkPathSearchPathFunction,
      Optional<SourcePath> prefixHeader,
      CxxInferTools inferTools,
      DebugPathSanitizer sanitizer) {
    super(buildRuleParams, pathResolver);
    this.preprocessorFlags = preprocessorFlags;
    this.compilerFlags = compilerFlags;
    this.input = input;
    this.inputType = inputType;
    this.output = output;
    this.includeRoots = includeRoots;
    this.systemIncludeRoots = systemIncludeRoots;
    this.headerMaps = headerMaps;
    this.frameworkRoots = frameworkRoots;
    this.frameworkPathSearchPathFunction = frameworkPathSearchPathFunction;
    this.prefixHeader = prefixHeader;
    this.inferTools = inferTools;
    this.resultsDir = BuildTargets.getGenPath(this.getBuildTarget(), "infer-out-%s");
    this.sanitizer = sanitizer;
  }

  private CxxToolFlags getSearchPathFlags() {
    return CxxToolFlags.explicitBuilder()
        .addAllRuleFlags(
            MoreIterables.zipAndConcat(
                Iterables.cycle("-include"),
                FluentIterable.from(prefixHeader.asSet())
                    .transform(getResolver().deprecatedPathFunction())
                    .transform(Functions.toStringFunction())))
        .addAllRuleFlags(
            MoreIterables.zipAndConcat(
                Iterables.cycle("-I"),
                Iterables.transform(headerMaps, Functions.toStringFunction())))
        .addAllRuleFlags(
            MoreIterables.zipAndConcat(
                Iterables.cycle("-I"),
                Iterables.transform(includeRoots, Functions.toStringFunction())))
        .addAllRuleFlags(
            MoreIterables.zipAndConcat(
                Iterables.cycle("-isystem"),
                Iterables.transform(systemIncludeRoots, Functions.toStringFunction())))
        .addAllRuleFlags(
            MoreIterables.zipAndConcat(
                Iterables.cycle("-F"),
                FluentIterable.from(frameworkRoots)
                    .transform(
                        Functions.compose(
                            Functions.toStringFunction(),
                            frameworkPathSearchPathFunction))
                    .toSet()))
        .build();
  }

  private ImmutableList<String> getFrontendCommand() {
    // TODO(martinoluca): Add support for extra arguments (and add them to the rulekey)
    ImmutableList.Builder<String> commandBuilder = ImmutableList.builder();
    return commandBuilder
        .addAll(this.inferTools.topLevel.getCommandPrefix(getResolver()))
        .add("-a", "capture")
        .add("--project_root", getProjectFilesystem().getRootPath().toString())
        .add("--out", resultsDir.toString())
        .add("--")
        .add("clang")
        .addAll(
            CxxToolFlags.concat(preprocessorFlags, getSearchPathFlags(), compilerFlags)
                .getAllFlags())
        .add("-x", inputType.getLanguage())
        .add("-o", output.toString()) // TODO(martinoluca): Use -fsyntax-only for better perf
        .add("-c")
        .add(getResolver().deprecatedGetPath(input).toString())
        .build();
  }

  @Override
  public ImmutableList<Step> getBuildSteps(
      BuildContext context, BuildableContext buildableContext) {
    ImmutableList<String> frontendCommand = getFrontendCommand();
    buildableContext.recordArtifact(this.getPathToOutput());

    return ImmutableList.<Step>builder()
        .add(new MkdirStep(getProjectFilesystem(), resultsDir))
        .add(new MkdirStep(getProjectFilesystem(), output.getParent()))
        .add(new DefaultShellStep(
                getProjectFilesystem().getRootPath(),
                frontendCommand,
                inferTools.topLevel.getEnvironment(getResolver())))
        .build();
  }

  @Override
  public Path getPathToOutput() {
    return this.resultsDir;
  }

  @Override
  public RuleKeyBuilder appendToRuleKey(RuleKeyBuilder builder) {
    // Sanitize any relevant paths in the flags we pass to the preprocessor, to prevent them
    // from contributing to the rule key.
    return builder
        .setReflectively(
            "platformPreprocessorFlags",
            sanitizer.sanitizeFlags(preprocessorFlags.getPlatformFlags()))
        .setReflectively(
            "rulePreprocessorFlags",
            sanitizer.sanitizeFlags(preprocessorFlags.getRuleFlags()))
        .setReflectively(
            "platformCompilerFlags",
            sanitizer.sanitizeFlags(compilerFlags.getPlatformFlags()))
        .setReflectively(
            "ruleCompilerFlags",
            sanitizer.sanitizeFlags(compilerFlags.getRuleFlags()));
  }
}
