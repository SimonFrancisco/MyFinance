import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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
        val localProperties = Properties()
        localProperties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField(
            "String",
            "API_KEY",
            "\"${localProperties.getProperty("API_KEY")}\""
        )
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://shmr-finance.ru/api/v1/\"")
        }
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
        buildConfig = true
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

    implementation(libs.bundles.serialization)

    ksp(libs.bundles.ksp)

    implementation(libs.bundles.http)
    implementation(libs.bundles.retrofit)
    implementation(libs.json.serialization)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.lottie.compose)

    implementation(libs.room.core)

    implementation(libs.dagger2)


}