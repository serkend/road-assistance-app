import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.core.common.testing.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
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
        buildConfig = true
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
    }
}

dependencies {
    //Modules
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))

    implementation(project(":features:auth:presentation"))
    implementation(project(":features:chats:presentation"))
    implementation(project(":features:map:presentation"))
    implementation(project(":features:profile:presentation"))
//    androidTestImplementation(project(":core:ui-test"))

    // Core KTX
    implementation(libs.coreKtx)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // ViewBinding
    implementation(libs.viewBindingDelegate)

    // UI components
    implementation(libs.constraintLayout)
    implementation(libs.material)
    implementation(libs.appCompat)

    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

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
    testImplementation(libs.hiltTesting)

    // Testing
//    implementation(libs.junit)
//    implementation(libs.coroutinesTest)
//    implementation(libs.hiltTesting)
//    implementation(libs.androidxTestRunner)
//    implementation(libs.mockkAndroid)

}