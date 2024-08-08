plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.ui_test"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "com.example.core.common.testing.HiltTestRunner"
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
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
//    androidTestImplementation(project(":core:navigation"))
//    implementation(project(":app"))
//    androidTestImplementation(project(":app"))

    implementation(libs.appCompat)
    implementation(libs.material)

    implementation(libs.kotlinStdlib)
    implementation(libs.coreKtx)

    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)
    implementation(libs.roomKtx)

    implementation(libs.playServicesLocation)
    implementation(libs.playServicesMaps)

    // Testing
    implementation(libs.junit)
    implementation(libs.coroutinesTest)
    implementation(libs.hiltTesting)
    implementation(libs.androidxTestRunner)
    implementation(libs.mockkAndroid)
    implementation(libs.fragmentTesting)
    implementation(libs.navigationTesting)

    kaptTest(libs.hiltCompiler)
    kaptAndroidTest(libs.hiltCompiler)
}