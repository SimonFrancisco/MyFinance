package francisco.simon.core.domain.preferences

import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface LanguagePreferences {
    val locale: Flow<Locale>
    suspend fun setLocale(locale: String)
}