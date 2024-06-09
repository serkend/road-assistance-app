#!/bin/bash

# Array of features
features=("auth" "chat" "map" "profile")

# Base directories
base_dir="features"
build_gradle_content="
plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}

android {
    compileSdkVersion 34
    defaultConfig {
        minSdkVersion 24
        targetSdkVersion 34
        testInstrumentationRunner 'androidx.test.runner.AndroidJUnitRunner'
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = '17'
    }
}

dependencies {
    implementation project(':core')
    implementation project(':features:\${feature}:domain')
    implementation project(':features:\${feature}:data')
    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
    implementation 'com.google.dagger:hilt-android:2.46'
    kapt 'com.google.dagger:hilt-compiler:2.46'
    implementation 'androidx.room:room-runtime:2.6.1'
    kapt 'androidx.room:room-compiler:2.6.1'
}
"

# Loop through each feature and create necessary directories and files
for feature in "${features[@]}"
do
    # Create directories
    mkdir -p "$base_dir/$feature/presentation/src/main"
    mkdir -p "$base_dir/$feature/domain/src/main"
    mkdir -p "$base_dir/$feature/data/src/main"

    # Create build.gradle.kts files with the appropriate content
    echo "${build_gradle_content/\$\{feature\}/$feature}" > "$base_dir/$feature/presentation/build.gradle.kts"
    echo "${build_gradle_content/\$\{feature\}/$feature}" > "$base_dir/$feature/domain/build.gradle.kts"
    echo "${build_gradle_content/\$\{feature\}/$feature}" > "$base_dir/$feature/data/build.gradle.kts"

    # Add module to settings.gradle.kts
    echo "include(':features:$feature:presentation')" >> settings.gradle.kts
    echo "include(':features:$feature:domain')" >> settings.gradle.kts
    echo "include(':features:$feature:data')" >> settings.gradle.kts
done