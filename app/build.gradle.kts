import org.intellij.lang.annotations.Language
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id(libs.plugins.kotlin.multiplatform.id)
    // id(libs.plugins.android.application.id)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

// region buildconfig
@Language("Kotlin")
// language=kotlin
val BuildConfig = """
    package ${Config.namespace}

    object BuildFlags {
        const val VersionName = "${Config.versionName}"
    }
""".trimIndent()

val generateBuildConfig by tasks.registering(Sync::class) {
    from(resources.text.fromString(BuildConfig)) {
        rename { "BuildFlags.kt" }
        into(Config.namespace.replace(".", "/"))
    }
    // the target directory
    into(layout.buildDirectory.dir("generated/kotlin/src/commonMain"))
}
// endregion

kotlin {
    applyDefaultHierarchyTemplate()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "app"
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
                export = true
            }
            testTask { enabled = false }
        }
    }

    jvm("desktop")

    // region disabled
    // androidTarget().compilations.all {
    //     compileTaskProvider.configure {
    //         compilerOptions {
    //             jvmTarget = Config.jvmTarget
    //             freeCompilerArgs.addAll(Config.jvmCompilerArgs)
    //         }
    //     }
    // }

    // sequence {
    //     yield(iosX64())
    //     yield(iosArm64())
    //     yield(iosSimulatorArm64())
    //     // yield(macosArm64())
    //     // yield(macosX64())
    // }.toList()
    //endregion

    sourceSets {
        val desktopMain by getting
        val wasmJsMain by getting

        all {
            languageSettings {
                progressiveMode = true
                Config.optIns.forEach { optIn(it) }
            }
        }
        commonMain {
            kotlin.srcDir(generateBuildConfig.map { it.destinationDir })
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.ui)
                implementation(compose.components.resources)

                implementation(libs.kotlin.datetime)
                implementation(libs.kotlin.io)
                implementation(libs.apiresult)

                implementation(libs.bundles.serialization)
                implementation(libs.bundles.kmputils)
                implementation(libs.bundles.kodein)
            }
        }
        desktopMain.dependencies {
            implementation(libs.kotlin.coroutines.swing)
            implementation(compose.desktop.currentOs)
        }
        wasmJsMain.dependencies {

        }
    } // sets
}
// region android
// android {
//     namespace = Config.Sample.namespace
//     configureAndroid()
//     buildFeatures {
//         viewBinding = true
//         buildConfig = true
//         compose = true
//     }
//     defaultConfig {
//         compileSdk = Config.compileSdk
//         applicationId = Config.artifactId
//         minSdk = Config.appMinSdk
//         targetSdk = Config.targetSdk
//         versionCode = Config.versionCode
//         versionName = Config.versionName
//     }
//     applicationVariants.all {
//         setProperty("archivesBaseName", Config.Sample.namespace)
//         outputs
//             .matching { "apk" in it.outputFile.extension }
//             .all {
//                 this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
//                 outputFileName = "${Config.Sample.namespace}.apk"
//             }
//     }
//     signingConfigs {
//         val props by localProperties()
//         val passwd = props["signing.password"].toString().trim()
//         create("release") {
//             keyAlias = "key"
//             keyPassword = passwd
//             storeFile = File(rootDir, "certificates/keystore.jks")
//             storePassword = passwd.trim()
//         }
//     }
//     buildTypes {
//         debug {
//             applicationIdSuffix = ".debug"
//             signingConfig = signingConfigs.getByName("debug")
//             versionNameSuffix = "-debug"
//             isShrinkResources = Config.isMinifyEnabledDebug
//         }
//         release {
//             ndk.debugSymbolLevel = "FULL"
//             isShrinkResources = true
//             isMinifyEnabled = true
//             signingConfig = signingConfigs.getByName("release")
//         }
//     }
//     androidResources {
//         generateLocaleConfig = false
//     }
// }

dependencies {
    // means androidDebugImplementation
    // debugImplementation(projects.debugger.debuggerPlugin)
}
// endregion

compose {
    resources {
        packageOfResClass = Config.namespace
        publicResClass = false
    }
    // android { }
    web { }
    desktop {
        application {
            mainClass = "${Config.namespace}.MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Deb, TargetFormat.Exe)
                packageName = Config.namespace
                packageVersion = Config.majorVersionName
                description = Config.description
                vendor = Config.vendorName
                licenseFile = rootProject.rootDir.resolve(Config.licenseFile)
                macOS {
                    packageName = Config.name
                    dockName = Config.name
                    setDockNameSameAsPackageName = false
                    bundleID = Config.namespace
                    appCategory = "public.app-category.developer-tools"
                    // iconFile = iconDir.resolve("icon_macos.icns")
                }
                windows {
                    dirChooser = true
                    menu = false
                    shortcut = true
                    perUserInstall = true
                    upgradeUuid = Config.appId
                    // iconFile = iconDir.resolve("favicon.ico")
                }
                linux {
                    debMaintainer = Config.supportEmail
                    appCategory = "Development"
                    // iconFile = iconDir.resolve("icon_512.png")
                }
            }
        }
    }
}
