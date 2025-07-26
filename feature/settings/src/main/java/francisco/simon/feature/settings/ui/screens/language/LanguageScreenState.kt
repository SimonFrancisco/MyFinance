package francisco.simon.feature.settings.ui.screens.language

import java.util.Locale

internal sealed class LanguageScreenState {
    data object Loading : LanguageScreenState()
    data class Success(val locale: Locale) : LanguageScreenState()
}