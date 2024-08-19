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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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

    //Firebase
    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    //Compose
    implementation(libs.composeUi)
    implementation(libs.composeMaterial3)
    implementation(libs.composeActivity)
    implementation(libs.composeTooling)
    implementation(libs.composeRuntime)

    // Testing
    implementation(libs.junit)
    implementation(libs.coroutinesTest)
    implementation(libs.hiltTesting)
    implementation(libs.androidxTestRunner)

}