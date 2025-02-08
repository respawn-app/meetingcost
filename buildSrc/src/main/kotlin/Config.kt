@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "MissingPackageDeclaration",
    "UndocumentedPublicClass",
    "UndocumentedPublicProperty",
    "MaxLineLength"
)

import org.gradle.api.JavaVersion
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {

    const val group = "pro.respawn"
    const val artifact = "meetingcost"

    const val artifactId = "$group.$artifact"

    const val majorRelease = 1
    const val minorRelease = 0
    const val patch = 0
    const val postfix = "" // include dash (-)
    const val versionCode = 1

    const val majorVersionName = "$majorRelease.$minorRelease.$patch"
    const val versionName = "$majorVersionName$postfix"
    const val url = "https://github.com/respawn-app/meetingcost"
    const val developerUrl = "https://respawn.pro"
    const val licenseFile = "LICENSE.txt"
    const val licenseName = "The Apache Software License, Version 2.0"
    const val licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0.txt"
    const val scmUrl = "https://github.com/respawn-app/meetingcost.git"
    const val description =
        """Online realtime meeting cost calculator."""
    const val supportEmail = "hello@respawn.pro"
    const val vendorName = "Respawn Open Source Team"
    const val vendorId = "respawn-app"
    const val name = "MeetingCost"
    const val appId = "cd5e10bc-f0c2-42db-b43b-b485b55f2e5f"

    val jvmTarget = JvmTarget.JVM_11
    val javaVersion = JavaVersion.VERSION_11
    val optIns = listOf(
        "kotlinx.coroutines.ExperimentalCoroutinesApi",
        "kotlinx.coroutines.FlowPreview",
        "kotlin.RequiresOptIn",
        "kotlin.experimental.ExperimentalTypeInference",
        "kotlin.uuid.ExperimentalUuidApi",
        "kotlin.contracts.ExperimentalContracts",
        "org.jetbrains.compose.resources.ExperimentalResourceApi"
    )
    val compilerArgs = listOf(
        "-Xbackend-threads=0", // parallel IR compilation
        "-Xexpect-actual-classes",
        "-Xwasm-use-new-exception-proposal",
        "-Xconsistent-data-class-copy-visibility",
        "-Xsuppress-warning=NOTHING_TO_INLINE",
        "-Xsuppress-warning=UNUSED_ANONYMOUS_PARAMETER",
        "-Xwasm-debugger-custom-formatters"
    )
    val jvmCompilerArgs = buildList {
        addAll(compilerArgs)
        add("-Xjvm-default=all") // enable all jvm optimizations
        add("-Xcontext-receivers")
        add("-Xstring-concat=inline")
        add("-Xlambdas=indy")
        add("-Xjdk-release=${jvmTarget.target}")
    }

    // android
    const val compileSdk = 35
    const val targetSdk = compileSdk
    const val minSdk = 26
    const val appMinSdk = 26
    const val namespace = artifactId
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val isMinifyEnabledRelease = false
    const val isMinifyEnabledDebug = false
    const val defaultProguardFile = "proguard-android-optimize.txt"
    const val proguardFile = "proguard-rules.pro"
    const val consumerProguardFile = "consumer-rules.pro"

    // position reflects the level of stability, order is important
    val stabilityLevels = listOf("snapshot", "eap", "preview", "alpha", "beta", "m", "cr", "rc")
    val minStabilityLevel = stabilityLevels.indexOf("beta")

    object Detekt {

        const val configFile = "detekt.yml"
        val includedFiles = listOf("**/*.kt", "**/*.kts")
        val excludedFiles = listOf("**/resources/**", "**/build/**", "**/.idea/**")
    }
}
