package francisco.simon.core.data.network.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import francisco.simon.core.domain.preferences.ColorSchemePreferences
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import francisco.simon.core.domain.utils.theme.getColorSchemeByName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ColorSchemePreferencesImpl(
    context: Context
) : ColorSchemePreferences {

    private val Context.datastore by preferencesDataStore(name = "ColorSchemePreferences")
    private val dataStore = context.datastore

    override val colorScheme: Flow<MyFinanceColorScheme>
        get() = dataStore.data.map { preferences ->
            getColorSchemeByName(preferences[COLOR_SCHEME_KEY])
        }

    override suspend fun setColorScheme(colorScheme: MyFinanceColorScheme) {
        dataStore.edit { preferences ->
            preferences[COLOR_SCHEME_KEY] = colorScheme.name
        }
    }

    private companion object {
        val COLOR_SCHEME_KEY = stringPreferencesKey("color_scheme")
    }

}