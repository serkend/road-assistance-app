plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.core.uikit"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    // View
    api(libs.viewBindingDelegate)
    api(libs.constraintLayout)
    api(libs.material)
    api(libs.appCompat)

    // Lifecycle
    api(libs.lifecycleRuntime)
    api(libs.lifecycleViewModel)

    // Fragment and RecyclerView
    api(libs.fragmentKtx)
    api(libs.recyclerview)

    // Compose
    api(libs.composeUi)
    api(libs.composeCoil)
    api(libs.composeMaterial3)
    api(libs.composeActivity)
    debugApi(libs.composeTooling)
    api(libs.composeToolingPreview)
    api(libs.composeRuntime)
    api(libs.hiltNavigationCompose)
    api(libs.navigationCompose)
}