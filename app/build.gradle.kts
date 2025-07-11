
plugins {
    alias(libs.plugins.custom.android.application)
}

android {
    namespace = "francisco.simon.myfinance"

    defaultConfig {
        applicationId = "francisco.simon.myfinance"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.feature.category)
    implementation(projects.feature.account)
    implementation(projects.feature.income)

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