pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.version.toml"))
        }
    }
}

rootProject.name = "RoadAssist"

include(":app")
include(":core")
include(":core:common")
include(":core:data")
include(":core:domain")

include(":features")
include(":features:map")
include(":features:chats")
include(":features:profile")
include(":features:auth")
include(":features:auth:presentation")
include(":features:chats:presentation")
include(":features:map:presentation")
include(":features:profile:presentation")

