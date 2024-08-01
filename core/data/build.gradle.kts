plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kapt)
    id(Plugins.daggerHilt)
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.data"
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
    compileOptions {
        sourceCompatibility = Config.compatibleJavaVersion
        targetCompatibility = Config.compatibleJavaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    implementation(libs.kotlinStdlib)
    implementation(libs.coreKtx)

    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)

    implementation(libs.playServicesLocation)
    implementation(libs.playServicesMaps)
}