plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.features.map.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MAPS_API_KEY", "\"your_maps_api_key_here\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    // Kotlin
    implementation(libs.kotlinStdlib)

    // Core KTX
    implementation(libs.coreKtx)

    // Firebase
    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    // Google API
    implementation(libs.playServicesLocation)
    implementation(libs.playServicesMaps)

    // ViewBinding
    implementation(libs.viewBindingDelegate)

    // UI components
    implementation(libs.constraintLayout)
    implementation(libs.material)
    implementation(libs.appCompat)

    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.okhttp)
    implementation(libs.okhttpLogging)

    // Lifecycle
    implementation(libs.lifecycleRuntime)
    implementation(libs.lifecycleViewModel)

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
    testImplementation(libs.mockito)
    testImplementation(libs.coroutinesTest)

    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.composeUiTestManifest)
}
