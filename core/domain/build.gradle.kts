plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kapt)
    id(Plugins.daggerHilt)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.domain"
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

    implementation(project(":core:common"))

    implementation("androidx.core:core-ktx:1.10.1")

    //Firebase
    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    //DI
    implementation(Libs.Application.DependencyInjection.hilt)
    kapt(Libs.Application.DependencyInjection.hilt_compiler)

}