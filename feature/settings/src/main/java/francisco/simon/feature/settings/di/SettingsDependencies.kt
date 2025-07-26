package francisco.simon.feature.settings.di

import francisco.simon.core.domain.preferences.AboutPreferences
import francisco.simon.core.domain.preferences.ColorSchemePreferences
import francisco.simon.core.domain.preferences.LanguagePreferences
import francisco.simon.core.domain.preferences.SyncPreferences
import francisco.simon.core.domain.preferences.ThemeModePreferences

interface SettingsDependencies {
    fun getSyncPreferences(): SyncPreferences
    fun getThemeModePreferences(): ThemeModePreferences
    fun getColorSchemePreferences(): ColorSchemePreferences
    fun getAboutPreferences(): AboutPreferences
    fun languagePreferences(): LanguagePreferences
}