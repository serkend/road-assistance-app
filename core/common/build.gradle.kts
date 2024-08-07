plugins {
    id(Plugins.androidLibrary)
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.core.common"
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = Config.compatibleJavaVersion
        targetCompatibility = Config.compatibleJavaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    //Kotlin
    implementation(Libs.View.coreKtx)
    implementation("androidx.fragment:fragment-ktx:1.7.0")
    implementation(libs.recyclerview)
    implementation(libs.material)
    implementation(libs.appCompat)

    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
}