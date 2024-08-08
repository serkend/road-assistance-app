plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.core.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    //Kotlin
    implementation(libs.coreKtx)
    implementation(libs.fragmentKtx)
    implementation(libs.recyclerview)
    implementation(libs.material)
    implementation(libs.appCompat)

    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

    //Firebase
    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    // Testing
    implementation(libs.junit)
    implementation(libs.coroutinesTest)
    implementation(libs.hiltTesting)
    implementation(libs.androidxTestRunner)

}