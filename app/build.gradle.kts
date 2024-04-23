plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = Android.applicationId
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.applicationId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = Android.testInstrumentalRunner

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isDebuggable = Obfuscation.releaseDebuggable
            isMinifyEnabled = Obfuscation.releaseMinifyEnabled
            isShrinkResources = Obfuscation.releaseMinifyEnabled

            signingConfig = signingConfigs.getByName("debug")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
        debug {
            isDebuggable = Obfuscation.debugDebuggable
            isMinifyEnabled = Obfuscation.debugMinifyEnabled
            isShrinkResources = Obfuscation.debugMinifyEnabled

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
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
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources {
            resources.excludes.add("META-INF/AL2.0")
            resources.excludes.add("META-INF/LGPL2.1")
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Modules
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":common"))

    //Navigation
    implementation(Libs.Application.Navigation.navigation_fragment)
    implementation(Libs.Application.Navigation.navigation_ui)

    //View
    implementation(Libs.View.viewBindingDelegate)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(Libs.View.fragmentKtx)
    implementation(Libs.View.material)
    implementation(Libs.View.appCompat)

    //Kotlin
    implementation(Libs.View.coreKtx)

    //Firebase
    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.storage)
    implementation(Firebase.firestore)
    implementation(Firebase.auth)

    //Google API
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")

    //Lifecycle
    implementation(Libs.View.lifecycleRuntime)
    implementation(Libs.View.lifecycleViewModel)

    //DI
    implementation(Libs.Application.DependencyInjection.hilt)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Libs.Application.DependencyInjection.hilt_compiler)

    //View
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //Compose
    implementation(Libs.Compose.coilCompose)
    implementation(Libs.Compose.runtime)
    implementation(Libs.Boom.activityCompose)
    implementation(Libs.Boom.viewModelCompose)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.navigation)
    implementation(Libs.Compose.systemUiController)
    implementation(Libs.Compose.preview)
    implementation(Libs.Compose.tooling)
    implementation(Libs.Application.DependencyInjection.hiltNavigationCompose)

    //Tests
    testImplementation(Libs.View.Test.jUnit)
    androidTestImplementation(Libs.View.AndroidTest.jUnit)
    androidTestImplementation(Libs.View.AndroidTest.espresso)

}