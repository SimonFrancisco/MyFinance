
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.feature.category)
    implementation(projects.feature.account)
    implementation(projects.feature.income)
    implementation(projects.feature.expenses)
    implementation(projects.feature.settings)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)

    implementation(libs.bundles.android.ui)

    debugImplementation(libs.bundles.compose.debug)

    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.bundles.serialization)

    ksp(libs.dagger2.compiler)
    ksp(libs.dagger2.android.processor)

    implementation(libs.json.serialization)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.lottie.compose)

    implementation(libs.dagger2)

    implementation(libs.work.manager)

}