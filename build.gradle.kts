buildscript {
//    val kotlin_version by extra("1.9.10")
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Config.kotlinVersion}")
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version Config.gradleAndroidVersion apply false
    id("com.android.library") version Config.gradleAndroidVersion apply false
    id("org.jetbrains.kotlin.android") version Config.kotlinVersion apply false
    id("com.google.dagger.hilt.android") version Config.daggerVersion apply false
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}