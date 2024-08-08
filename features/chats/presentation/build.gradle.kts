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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))

    // Kotlin
    implementation(libs.kotlinStdlib)

    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

    // View
    implementation(libs.viewBindingDelegate)
    implementation(libs.constraintLayout)
    implementation(libs.material)
    implementation(libs.appCompat)

    // Firebase
    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    // Google API
    implementation(libs.playServicesLocation)
    implementation(libs.playServicesMaps)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.okhttpLogging)

    // Lifecycle
    implementation(libs.lifecycleRuntime)
    implementation(libs.lifecycleViewModel)

    // DI
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    // Fragment and RecyclerView
    implementation(libs.fragmentKtx)
    implementation(libs.recyclerview)

    // Compose
    implementation(libs.composeUi)
    implementation(libs.composeMaterial)
    implementation(libs.composeMaterial3)
    implementation(libs.composeActivity)

    // Glide
    implementation(libs.glide)
    kapt(libs.glideCompiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.coroutinesTest)

    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.composeUiTestManifest)
}
