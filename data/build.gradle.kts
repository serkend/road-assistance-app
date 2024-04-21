plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kapt)
    id(Plugins.daggerHilt)
    id("org.jetbrains.kotlin.android")
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

    implementation(project(Modules.common))
    implementation(project(Modules.domain))

    //Kotlin
    implementation(Libs.View.coreKtx)

    //Firebase
    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.storage)
    implementation(Firebase.firestore)
    implementation(Firebase.auth)

    //DI
    implementation(Libs.Application.DependencyInjection.hilt)
    kapt(Libs.Application.DependencyInjection.hilt_compiler)

    //Room
    implementation(Libs.Application.Database.roomRuntime)
    implementation(Libs.Application.Database.roomKtx)

}