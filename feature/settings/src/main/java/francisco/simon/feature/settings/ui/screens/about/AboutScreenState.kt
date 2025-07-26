package francisco.simon.feature.settings.ui.screens.about

internal sealed class AboutScreenState {
    data object Loading : AboutScreenState()
    data class Success(val appVersion: String) : AboutScreenState()
}