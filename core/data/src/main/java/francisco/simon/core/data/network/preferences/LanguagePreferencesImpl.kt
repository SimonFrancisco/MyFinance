package francisco.simon.core.data.network.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import francisco.simon.core.domain.preferences.LanguagePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguagePreferencesImpl(
    context: Context
) : LanguagePreferences {
    private val Context.datastore by preferencesDataStore(name = "LanguagePreferences")
    private val dataStore = context.datastore

    override val locale: Flow<Locale>
        get() = dataStore.data
            .map { preferences ->
                val languageTag = preferences[LOCALE_KEY] ?: Locale.getDefault().toLanguageTag()
                Locale.forLanguageTag(languageTag)
            }

    override suspend fun setLocale(locale: String) {
        dataStore.edit { preferences ->
            preferences[LOCALE_KEY] = Locale.forLanguageTag(locale).toLanguageTag()
        }
    }

    private companion object {
        val LOCALE_KEY = stringPreferencesKey("selected_locale")
    }
}