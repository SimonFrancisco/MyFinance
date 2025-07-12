plugins {
    alias(libs.plugins.custom.android.library)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "francisco.simon.core.ui"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.android.ui)

}