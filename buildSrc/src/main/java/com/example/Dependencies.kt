@file:Suppress("unused")

import org.gradle.api.JavaVersion

object Modules {
    const val domain = ":domain"
    const val data = ":data"
    const val common = ":common"
}

object Config {
    val compatibleJavaVersion = JavaVersion.VERSION_17
    const val jvmTarget = "17"
    const val kotlinVersion = "1.8.0"
    const val gradleAndroidVersion = "8.0.0"
    const val daggerVersion = "2.46"
}

object Android {
    const val applicationId = "com.example.roadAssist"
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 34

    const val versionCode = 1
    const val versionName = "1.0"

    const val testInstrumentalRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Obfuscation {
    const val releaseMinifyEnabled = true
    const val debugMinifyEnabled = false
    const val releaseDebuggable = false
    const val debugDebuggable = true
}

object Plugins {
    const val application = "com.android.application"
    const val kotlin = "org.jetbrains.kotlin.android"
    const val parcelable = "kotlin-parcelize"
    const val daggerHilt = "dagger.hilt.android.plugin"
    const val kapt = "kotlin-kapt"
    const val androidLibrary = "com.android.library"
}

object Project {
    object Dagger {
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Config.daggerVersion}"
    }
    object Android {
        const val androidGradle = "com.android.tools.build:gradle:${Config.gradleAndroidVersion}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Config.kotlinVersion}"
    }
}

object Firebase {
    private const val bom_version = "32.2.3"
    const val firebaseBom = "com.google.firebase:firebase-bom:$bom_version"
    const val auth = "com.google.firebase:firebase-auth-ktx"
    const val firestore = "com.google.firebase:firebase-firestore-ktx"
    const val storage = "com.google.firebase:firebase-storage-ktx"
    const val messaging = "com.google.firebase:firebase-messaging-ktx"
    const val analytics = "com.google.firebase:firebase-analytics"
}

object Libs {
    object View {
        const val appCompat = "androidx.appcompat:appcompat:1.5.1"
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val material = "com.google.android.material:material:1.9.0"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.6.1"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
        const val viewBindingDelegate = "com.github.kirich1409:viewbindingpropertydelegate-full:1.5.9"

        object Test {
            const val jUnit = "junit:junit:4.13.2"
            const val mockito = "org.mockito:mockito-core:4.1.0"
            const val unit_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
        }
        object AndroidTest {
            const val jUnit = "androidx.test.ext:junit:1.1.5"
            const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        }
    }
    object Compose {
        const val composeVersion = "1.4.1"
        const val kotlinCompiler = "1.4.21"
        private const val accompanist = "0.28.0"
        private const val coil = "2.2.2"

        const val ui = "androidx.compose.ui:ui:$composeVersion"
        const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val material = "androidx.compose.material:material:1.3.1"
        const val material3 = "androidx.compose.material3:material3:1.1.0"
        const val extended_icons = "androidx.compose.material:material-icons-extended:$composeVersion"
        const val activity = "androidx.activity:activity-compose:1.6.1"

//        const val navigation = "com.google.accompanist:accompanist-navigation-animation:$accompanist"
        const val navigation = "androidx.navigation:navigation-compose:2.5.3"

        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:0.27.0"

        const val coilCompose = "io.coil-kt:coil-compose:$coil"
        const val coilSvg = "io.coil-kt:coil-svg:$coil"
        const val coilGifs = "io.coil-kt:coil-gif:$coil"


        object Test {
            const val uiJunit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
            const val uiManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
        }

        object Debug {
            const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
        }
    }

    object Boom {
        private const val composeBoomVersion = "2023.01.00"

        const val composeBoom = "androidx.compose:compose-bom:$composeBoomVersion"

        /**
         * choice one
         */
        const val material3 = "androidx.compose.material3:material3"
        const val material2 = "androidx.compose.material:material"
        const val composeFoundation = "androidx.compose.foundation:foundation"
        const val composeUi = "androidx.compose.ui:ui"

        /**
         * Android Studio Preview support
         */
        const val toolingPreviw = "androidx.compose.ui:ui-tooling-preview"
        const val debugUiTooling = "androidx.compose.ui:ui-tooling"

        /**
         * ui tests
         */
        const val androidComposeTest = "androidx.compose.ui:ui-test-junit4"
        const val debugAndroidCompose = "androidx.compose.ui:ui-test-manifest"

        // Optional - Included automatically by material, only add when you need
        // the icons but not the material library (e.g. when using Material3 or a
        // custom design system based on Foundation)
        const val additionalIconsCompose = "androidx.compose.material:material-icons-core"
        // Optional - Add full set of material icons
        const val fullSetIconsCompose = "androidx.compose.material:material-icons-extended"
        // Optional - Add window size utils
        const val windowsSizeUtils = "androidx.compose.material3:material3-window-size-class"

        // Optional - Integration with activities
        const val activityCompose = "androidx.activity:activity-compose:1.6.1"
        // Optional - Integration with ViewModels
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        // Optional - Integration with LiveData
        const val liveDataCompose = "androidx.compose.runtime:runtime-livedata"
        // Optional - Integration with RxJava
        const val rxJavaCompose = "androidx.compose.runtime:runtime-rxjava2"

    }

    object Application {
        object DependencyInjection {
            private const val koinVersion = "3.3.3"
            private const val koinCompose = "3.4.2"

            const val hilt = "com.google.dagger:hilt-android:${Config.daggerVersion}"
            const val kaptDagger = "com.google.dagger:hilt-compiler:${Config.daggerVersion}"
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"

            const val koinAndroid = "io.insert-koin:koin-android:$koinVersion"
            const val koinAndroidCompose = "io.insert-koin:koin-androidx-compose:$koinCompose"

            const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Config.daggerVersion}"
        }

        object Database {
            private const val roomVersion = "2.4.3"

            const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
            const val roomAnnotationProcessor = "androidx.room:room-compiler:$roomVersion"
            const val roomKapt = "androidx.room:room-compiler:$roomVersion"

            const val roomKtx = "androidx.room:room-ktx:$roomVersion"
            const val roomPaging = "androidx.room:room-paging:$roomVersion"
        }

        object DataStore {
            const val datastore = "androidx.datastore:datastore-preferences:1.0.0"
        }

        object Coroutines {
            private const val version = "1.6.3"

            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }

        object Navigation {
            private const val nav_version =  "2.7.1"
            const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
            const val navigation_ui = "androidx.navigation:navigation-ui-ktx:$nav_version"
        }

        object Network {
            object Retrofit {
                private const val version = "2.9.0"
                const val retrofit = "com.squareup.retrofit2:retrofit:$version"
                const val retrofit_gson = "com.squareup.retrofit2:converter-gson:$version"
            }
            object OkHttp {
                private const val version = "4.10.0"
                const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:$version"
                const val okhttp = "com.squareup.okhttp3:okhttp:$version"
            }

        }
    }
    object Gson {
        private const val gson_version =  "2.8.8"
        const val gson = "com.google.code.gson:gson:$gson_version"
    }
}