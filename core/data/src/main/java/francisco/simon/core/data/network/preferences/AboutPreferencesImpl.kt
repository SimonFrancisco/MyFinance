package francisco.simon.core.data.network.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import francisco.simon.core.domain.preferences.AboutPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AboutPreferencesImpl(
    context: Context
) : AboutPreferences {
    private val Context.datastore by preferencesDataStore(name = "AboutPreferences")
    private val dataStore = context.datastore

    override val appVersion: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[APP_VERSION_KEY] ?: ""
        }

    override suspend fun setAppVersion(version: String) {
        dataStore.edit { preferences ->
            preferences[APP_VERSION_KEY] = version
        }
    }

    private companion object {
        val APP_VERSION_KEY = stringPreferencesKey("app_version")
    }
}