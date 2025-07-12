plugins {
    alias(libs.plugins.custom.android.library)
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "francisco.simon.feature.income"
    buildFeatures {
        compose = true
    }
}


dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.android.ui)
    implementation(libs.dagger2)
    ksp(libs.dagger2.compiler)
    ksp(libs.dagger2.android.processor)
    implementation(libs.bundles.serialization)
}