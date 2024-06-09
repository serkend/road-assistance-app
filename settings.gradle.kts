pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RoadAssist"
include (":data")
include (":domain")
include(":app")
include(":common")
include(":features")
include(":features:map")
include(":features:chats")
include(":features:profile")
include(":features:auth")
