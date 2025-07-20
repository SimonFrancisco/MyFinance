package francisco.simon.feature.settings.di

import dagger.Component
import francisco.simon.feature.settings.ViewModelFactory

@SettingsScope
@Component(modules = [SettingsViewModelModule::class], dependencies = [SettingsDependencies::class])
internal interface SettingsComponent {
    fun getViewModelFactory(): ViewModelFactory
}