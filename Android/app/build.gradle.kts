plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.bidhubandroid"
    compileSdk = 34
    buildFeatures.viewBinding = true
    defaultConfig {
        applicationId = "com.example.bidhubandroid"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildToolsVersion = "34.0.0"
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(kotlin("script-runtime"))

    // Navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)

    // Activity
    implementation(libs.androidx.activity.ktx)

    // Image
    implementation(libs.glide)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)

    // Retrogit2
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.converter.gson)

    // swipe refresh
    implementation(libs.androidx.swiperefreshlayout)

    //paging
//    val paging_version = "3.3.2"
    implementation(libs.androidx.paging.runtime)

    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.common)

    // optional - RxJava2 support
    implementation(libs.androidx.paging.rxjava2)

    // optional - RxJava3 support
    implementation(libs.androidx.paging.rxjava3)

    // optional - Guava ListenableFuture support
    implementation(libs.androidx.paging.guava)

    // optional - Jetpack Compose integration
    implementation(libs.androidx.paging.compose)
    // paging END
    
    // toss
    implementation(libs.payment.sdk.android)
}