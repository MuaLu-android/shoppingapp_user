include(":core-model")
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
        maven ("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
    }
}

rootProject.name = "Userapp"
include(":app")
include(":core-adapter")
include(":core-Interface")
include(":core-retrofit")
include(":core-service")
include(":core-untils")
include(":mylibrary")
include(":core-payzalo")
include(":core-activity")
