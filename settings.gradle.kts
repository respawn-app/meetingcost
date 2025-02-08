enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    // REQUIRED for IDE module configuration to resolve IDE platform
    repositoriesMode = RepositoriesMode.PREFER_PROJECT
    repositories {
        // mavenLocal()
        google()
        mavenCentral()
    }
}

rootProject.name = "MeetingCost"

include(":app")
