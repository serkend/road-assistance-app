    plugins {
        id("com.android.library")
        id("org.jetbrains.kotlin.android")
        id("kotlin-kapt")
        id("dagger.hilt.android.plugin")
        id("com.google.gms.google-services")
    }

    android {
        namespace = "com.example.features.auth.presentation"
        compileSdk = 34

        defaultConfig {
            minSdk = 24
            targetSdk = 34
            testInstrumentationRunner = "com.example.core.common.testing.HiltTestRunner"
        }
        buildFeatures {
            viewBinding = true
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
        kotlinOptions {
            jvmTarget = "17"
        }
        kapt {
            correctErrorTypes = true
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
        implementation(project(":core:common"))
        implementation(project(":core:domain"))
        implementation(project(":core:data"))
        implementation(project(":core:navigation"))

        implementation(project(":core:ui-test"))
        implementation(project(":core:data-test"))
        androidTestImplementation(project(":core:ui-test"))

        // Kotlin
//        implementation(libs.kotlinStdlib)

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
        testImplementation(libs.mockk)
        testImplementation(libs.coroutinesTest)
        testImplementation(libs.hiltTesting)
        kaptTest(libs.hiltCompiler)
        kaptAndroidTest(libs.hiltCompiler)

        androidTestImplementation(libs.androidxJunit)
        androidTestImplementation(libs.mockkAndroid)
        androidTestImplementation(libs.espressoCore)
        androidTestImplementation(libs.hiltTesting)
        androidTestImplementation(libs.fragmentTesting)
        androidTestImplementation(libs.navigationTesting)
        androidTestImplementation(libs.truth)
        androidTestImplementation(libs.turbine)
    }