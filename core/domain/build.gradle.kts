plugins {
    alias(libs.plugins.custom.android.library)
}
android {
    namespace = "francisco.simon.core.domain"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}