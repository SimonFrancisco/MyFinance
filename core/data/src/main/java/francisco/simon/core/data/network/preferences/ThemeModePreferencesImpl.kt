package francisco.simon.core.data.network.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import francisco.simon.core.domain.preferences.ThemeModePreferences
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import francisco.simon.core.domain.utils.theme.getThemeModeByName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeModePreferencesImpl(
    context: Context
) : ThemeModePreferences {

    private val Context.datastore by preferencesDataStore(name = "ThemeModePreferences")
    private val dataStore = context.datastore
    override val themeMode: Flow<MyFinanceThemeMode>
        get() = dataStore.data.map { preferences ->
            getThemeModeByName(preferences[THEME_COLOR_KEY])
        }

    override suspend fun setThemeMode(themeMode: MyFinanceThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_COLOR_KEY] = themeMode.name
        }
    }

    private companion object {
        val THEME_COLOR_KEY = stringPreferencesKey("theme_mode")
    }

}