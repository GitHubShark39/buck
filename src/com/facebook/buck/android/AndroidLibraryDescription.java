/*
 * Copyright 2014-present Facebook, Inc.
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

package com.facebook.buck.android;

import com.facebook.buck.core.cell.resolver.CellPathResolver;
import com.facebook.buck.core.description.BuildRuleParams;
import com.facebook.buck.core.description.arg.HasDepsQuery;
import com.facebook.buck.core.description.arg.HasProvidedDepsQuery;
import com.facebook.buck.core.description.attr.ImplicitDepsInferringDescription;
import com.facebook.buck.core.model.BuildTarget;
import com.facebook.buck.core.model.Flavor;
import com.facebook.buck.core.model.Flavored;
import com.facebook.buck.core.model.targetgraph.BuildRuleCreationContextWithTargetGraph;
import com.facebook.buck.core.model.targetgraph.DescriptionWithTargetGraph;
import com.facebook.buck.core.rules.BuildRule;
import com.facebook.buck.core.rules.type.BuildRuleType;
import com.facebook.buck.core.sourcepath.SourcePath;
import com.facebook.buck.core.util.immutables.BuckStyleImmutable;
import com.facebook.buck.io.filesystem.ProjectFilesystem;
import com.facebook.buck.jvm.core.JavaAbis;
import com.facebook.buck.jvm.core.JavaLibrary;
import com.facebook.buck.jvm.java.JavaBuckConfig;
import com.facebook.buck.jvm.java.JavaLibraryDescription;
import com.facebook.buck.jvm.java.JavacFactory;
import com.facebook.buck.jvm.java.JavacOptions;
import com.facebook.buck.jvm.java.JavacOptionsFactory;
import com.facebook.buck.jvm.java.SourceJar;
import com.facebook.buck.jvm.java.toolchain.JavacOptionsProvider;
import com.facebook.buck.toolchain.ToolchainProvider;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import java.util.Optional;
import org.immutables.value.Value;

public class AndroidLibraryDescription
    implements DescriptionWithTargetGraph<AndroidLibraryDescriptionArg>,
        Flavored,
        ImplicitDepsInferringDescription<
            AndroidLibraryDescription.AbstractAndroidLibraryDescriptionArg> {
  public static final BuildRuleType TYPE = BuildRuleType.of("android_library");

  private static final Flavor DUMMY_R_DOT_JAVA_FLAVOR =
      AndroidLibraryGraphEnhancer.DUMMY_R_DOT_JAVA_FLAVOR;

  public enum JvmLanguage {
    JAVA,
    KOTLIN,
    SCALA,
  }

  private final JavaBuckConfig javaBuckConfig;
  private final ToolchainProvider toolchainProvider;
  private final AndroidLibraryCompilerFactory compilerFactory;

  public AndroidLibraryDescription(
      JavaBuckConfig javaBuckConfig,
      AndroidLibraryCompilerFactory compilerFactory,
      ToolchainProvider toolchainProvider) {
    this.javaBuckConfig = javaBuckConfig;
    this.compilerFactory = compilerFactory;
    this.toolchainProvider = toolchainProvider;
  }

  @Override
  public Class<AndroidLibraryDescriptionArg> getConstructorArgType() {
    return AndroidLibraryDescriptionArg.class;
  }

  @Override
  public BuildRule createBuildRule(
      BuildRuleCreationContextWithTargetGraph context,
      BuildTarget buildTarget,
      BuildRuleParams params,
      AndroidLibraryDescriptionArg args) {
    ProjectFilesystem projectFilesystem = context.getProjectFilesystem();
    if (buildTarget.getFlavors().contains(JavaLibrary.SRC_JAR)) {
        return new SourceJar(
            buildTarget,
            projectFilesystem,
            params,
            args.getSrcs(),
            args.getMavenCoords(),
            args.getMavenPomTemplate(),
            ImmutableSortedSet.of());
    }

    if (args.isSkipNonUnionRDotJava()) {
      Preconditions.checkArgument(
          args.getResourceUnionPackage().isPresent(),
          "union_package should be specified if skip_non_union_r_dot_java is set");
    }

    ToolchainProvider toolchainProvider = context.getToolchainProvider();
    boolean hasDummyRDotJavaFlavor = buildTarget.getFlavors().contains(DUMMY_R_DOT_JAVA_FLAVOR);
    JavacOptions javacOptions =
        JavacOptionsFactory.create(
            toolchainProvider
                .getByName(JavacOptionsProvider.DEFAULT_NAME, JavacOptionsProvider.class)
                .getJavacOptions(),
            buildTarget,
            projectFilesystem,
            context.getActionGraphBuilder(),
            args);
    JavacFactory javacFactory = JavacFactory.getDefault(toolchainProvider);
    AndroidLibrary.Builder androidLibraryBuilder =
        AndroidLibrary.builder(
            buildTarget,
            projectFilesystem,
            toolchainProvider,
            params,
            context.getActionGraphBuilder(),
            context.getCellPathResolver(),
            javaBuckConfig,
            javacFactory,
            javacOptions,
            args,
            compilerFactory.getCompiler(args.getLanguage().orElse(JvmLanguage.JAVA), javacFactory));

    if (hasDummyRDotJavaFlavor) {
      return androidLibraryBuilder.buildDummyRDotJava();
    } else if (JavaAbis.isAbiTarget(buildTarget)) {
      return androidLibraryBuilder.buildAbi();
    }
    return androidLibraryBuilder.build();
  }

  @Override
  public boolean hasFlavors(ImmutableSet<Flavor> flavors) {
    return flavors.isEmpty()
        || flavors.equals(ImmutableSet.of(JavaLibrary.SRC_JAR))
        || flavors.equals(ImmutableSet.of(DUMMY_R_DOT_JAVA_FLAVOR))
        || flavors.equals(ImmutableSet.of(JavaAbis.CLASS_ABI_FLAVOR))
        || flavors.equals(ImmutableSet.of(JavaAbis.SOURCE_ABI_FLAVOR))
        || flavors.equals(ImmutableSet.of(JavaAbis.SOURCE_ONLY_ABI_FLAVOR))
        || flavors.equals(ImmutableSet.of(JavaAbis.VERIFIED_SOURCE_ABI_FLAVOR));
  }

  @Override
  public void findDepsForTargetFromConstructorArgs(
      BuildTarget buildTarget,
      CellPathResolver cellRoots,
      AbstractAndroidLibraryDescriptionArg constructorArg,
      ImmutableCollection.Builder<BuildTarget> extraDepsBuilder,
      ImmutableCollection.Builder<BuildTarget> targetGraphOnlyDepsBuilder) {
    compilerFactory
        .getCompiler(
            constructorArg.getLanguage().orElse(JvmLanguage.JAVA),
            JavacFactory.getDefault(toolchainProvider))
        .addTargetDeps(extraDepsBuilder, targetGraphOnlyDepsBuilder);
  }

  public interface CoreArg
      extends JavaLibraryDescription.CoreArg,
          AndroidKotlinCoreArg,
          HasDepsQuery,
          HasProvidedDepsQuery {
    Optional<SourcePath> getManifest();

    Optional<String> getResourceUnionPackage();

    @Value.Default
    default boolean isSkipNonUnionRDotJava() {
      return false;
    }

    Optional<String> getFinalRName();
  }

  @BuckStyleImmutable
  @Value.Immutable(copy = true)
  interface AbstractAndroidLibraryDescriptionArg extends CoreArg {}
}
