import java.util.Properties

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
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties()
        localProperties.load(project.rootProject.file("local.properties").inputStream())

        val mapsApiKey = localProperties.getProperty("MAPS_API_KEY")
            ?: throw GradleException("MAPS_API_KEY not found in local.properties")

        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")
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
    buildFeatures {
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

//    implementation(libs.kotlinStdlib)
    implementation(libs.coreKtx)

    implementation(libs.firebaseStorage)
    implementation(libs.firebaseFirestore)
    implementation(libs.firebaseAuth)

    // Google API
    implementation(libs.playServicesLocation)
    implementation(libs.playServicesMaps)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)
    implementation(libs.roomKtx)

    implementation(libs.retrofit)
    implementation(libs.retrofitGson)

    implementation(libs.playServicesLocation)
    implementation(libs.playServicesMaps)

    implementation(libs.workManager)

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