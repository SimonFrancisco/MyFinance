package francisco.simon.myfinance.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import francisco.simon.core.data.network.preferences.AboutPreferencesImpl
import francisco.simon.core.data.network.preferences.ColorSchemePreferencesImpl
import francisco.simon.core.data.network.preferences.LanguagePreferencesImpl
import francisco.simon.core.data.network.preferences.SyncPreferencesImpl
import francisco.simon.core.data.network.preferences.ThemeModePreferencesImpl
import francisco.simon.core.domain.preferences.AboutPreferences
import francisco.simon.core.domain.preferences.ColorSchemePreferences
import francisco.simon.core.domain.preferences.LanguagePreferences
import francisco.simon.core.domain.preferences.SyncPreferences
import francisco.simon.core.domain.preferences.ThemeModePreferences
import francisco.simon.myfinance.di.ApplicationScope

@Module
internal object PreferencesModule {

    @[ApplicationScope Provides]
    fun provideSyncPreferences(context: Context): SyncPreferences {
        return SyncPreferencesImpl(context)
    }

    @[ApplicationScope Provides]
    fun provideColorSchemePreferences(context: Context): ColorSchemePreferences {
        return ColorSchemePreferencesImpl(context)
    }

    @[ApplicationScope Provides]
    fun provideThemeModePreferences(context: Context): ThemeModePreferences {
        return ThemeModePreferencesImpl(context)
    }

    @[ApplicationScope Provides]
    fun provideAboutPreferences(context: Context): AboutPreferences {
        return AboutPreferencesImpl(context)
    }

    @[ApplicationScope Provides]
    fun provideLanguagePreferences(context: Context): LanguagePreferences {
        return LanguagePreferencesImpl(context)
    }

}