pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyFinance"
include(":app")
include(":template:android-library")
include(":template:kotlin-library")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":feature:category")
include(":feature:account")
include(":feature:income")
include(":feature:expenses")
include(":feature:settings")
