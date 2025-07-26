package francisco.simon.feature.settings.domain.language

import francisco.simon.core.domain.preferences.LanguagePreferences
import javax.inject.Inject

internal class EditLocaleUseCase @Inject constructor(
    private val languagePreferences: LanguagePreferences
) {
    suspend operator fun invoke(locale: String) {
        languagePreferences.setLocale(locale)
    }
}