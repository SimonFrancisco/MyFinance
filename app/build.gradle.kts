import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.custom.android.application)
}

android {
    namespace = "francisco.simon.myfinance"

    defaultConfig {
        applicationId = "francisco.simon.myfinance"
        versionCode = 1
        versionName = "1.0"

        val localProperties = Properties()
        localProperties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField(
            "String",
            "API_KEY",
            "\"${localProperties.getProperty("API_KEY")}\""
        )
        buildConfigField("String", "BASE_URL", "\"https://shmr-finance.ru/api/v1/\"")
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