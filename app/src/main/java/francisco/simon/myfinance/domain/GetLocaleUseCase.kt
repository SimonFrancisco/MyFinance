package francisco.simon.myfinance.domain

import francisco.simon.core.domain.preferences.LanguagePreferences
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class GetLocaleUseCase @Inject constructor(
    private val languagePreferences: LanguagePreferences
) {
    operator fun invoke(): Flow<Locale> {
        return languagePreferences.locale
    }
}