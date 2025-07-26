package francisco.simon.feature.settings.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SettingsGraph {
    @Serializable
    data object SettingsRoute

    @Serializable
    data object SyncRoute

    @Serializable
    data object PrimaryColorRoute

    @Serializable
    data object AboutRoute

    @Serializable
    data object LanguageRoute
}