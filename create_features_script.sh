#!/bin/bash

# Array of features
features=("auth" "chats" "map" "profile")

# Base directories
base_dir="features"

# Presentation build.gradle.kts content
presentation_gradle_content=$(cat <<EOL
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.features.__FEATURE__.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:__FEATURE__:domain"))
    implementation(libs.coreKtx)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.lifecycleRuntime)
    implementation(libs.lifecycleViewModel)
}
EOL
)

# Domain build.gradle.kts content
domain_gradle_content=$(cat <<EOL
plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.kotlinStdlib)
}
EOL
)

# Data build.gradle.kts content
data_gradle_content=$(cat <<EOL
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.features.__FEATURE__.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:__FEATURE__:domain"))
    implementation(libs.coreKtx)
    implementation(libs.appCompat)
    implementation(platform(libs.firebaseBom))
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)
}
EOL
)

# Loop through each feature and create necessary directories and files
for feature in "${features[@]}"
do
    # Create directories
    mkdir -p "$base_dir/$feature/presentation/src/main"
    mkdir -p "$base_dir/$feature/domain/src/main"
    mkdir -p "$base_dir/$feature/data/src/main"

    # Create build.gradle.kts files with the appropriate content
    echo "${presentation_gradle_content//__FEATURE__/$feature}" > "$base_dir/$feature/presentation/build.gradle.kts"
    echo "${domain_gradle_content//__FEATURE__/$feature}" > "$base_dir/$feature/domain/build.gradle.kts"
    echo "${data_gradle_content//__FEATURE__/$feature}" > "$base_dir/$feature/data/build.gradle.kts"

    # Add module to settings.gradle.kts
    echo "include(':features:$feature:presentation')" >> settings.gradle.kts
    echo "include(':features:$feature:domain')" >> settings.gradle.kts
    echo "include(':features:$feature:data')" >> settings.gradle.kts
done
