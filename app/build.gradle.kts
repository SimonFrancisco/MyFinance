plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "francisco.simon.myfinance"
    compileSdk = 35

    defaultConfig {
        applicationId = "francisco.simon.myfinance"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)
    implementation(libs.bundles.android.ui)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.bundles.android.test)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.serialization)
    ksp(libs.bundles.ksp)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.lottie.compose)


}