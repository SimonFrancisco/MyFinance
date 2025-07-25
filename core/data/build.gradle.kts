import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.custom.android.library)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
}
android {
    namespace = "francisco.simon.core.data"
    defaultConfig {
        val localProperties = Properties()
        localProperties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField(
            "String",
            "API_KEY",
            "\"${localProperties.getProperty("API_KEY")}\""
        )
        buildConfigField("String", "BASE_URL", "\"https://shmr-finance.ru/api/v1/\"")
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.data.store)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    ksp(libs.room.compiler)
    implementation(libs.json.serialization)
    api(libs.room.core)
    implementation(libs.bundles.http)
    implementation(libs.bundles.retrofit)
}