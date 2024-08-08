plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "com.example.core.common.testing.HiltTestRunner"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(":core:domain"))
    testImplementation(project(":core:common"))

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
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutinesTest)
    kaptTest(libs.hiltCompiler)
    kaptAndroidTest(libs.hiltCompiler)

    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.composeUiTestManifest)
    androidTestImplementation(libs.hiltTesting)
    androidTestImplementation(libs.truth)
    testImplementation(libs.hiltTesting)
}