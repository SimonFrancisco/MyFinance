package francisco.simon.feature.settings.di

import francisco.simon.core.domain.preferences.SyncPreferences

interface SettingsDependencies {
    fun getSyncPreferences(): SyncPreferences
}