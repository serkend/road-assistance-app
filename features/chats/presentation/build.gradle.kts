plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.features.chats.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))

    implementation(project(":core:ui-test"))
    implementation(project(":core:data-test"))
    androidTestImplementation(project(":core:ui-test"))

    // DI
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    // Glide
    implementation(libs.glide)
    kapt(libs.glideCompiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.hiltTesting)
    kaptTest(libs.hiltCompiler)
    kaptAndroidTest(libs.hiltCompiler)

    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.mockkAndroid)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.hiltTesting)
    androidTestImplementation(libs.fragmentTesting)
    androidTestImplementation(libs.navigationTesting)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.composeUiTestJunit4)
    debugImplementation(libs.composeUiTestManifest)
}
