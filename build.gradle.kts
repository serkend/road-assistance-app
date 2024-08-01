buildscript {
    dependencies {
        classpath(libs.navigationSafeArgs)
        classpath(libs.googleServices)
    }
    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version libs.versions.gradleAndroid apply false
    id("com.android.library") version libs.versions.gradleAndroid apply false
    id("org.jetbrains.kotlin.android") version libs.versions.kotlin apply false
    id("com.google.dagger.hilt.android") version libs.versions.daggerHilt apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
