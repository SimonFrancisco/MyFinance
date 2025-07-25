package francisco.simon.feature.settings.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.settings.ui.screens.about.AboutScreenViewModel
import francisco.simon.feature.settings.ui.screens.language.LanguageScreenViewModel
import francisco.simon.feature.settings.ui.screens.primary_color.PrimaryColorScreenViewModel
import francisco.simon.feature.settings.ui.screens.settings.SettingsViewModel
import francisco.simon.feature.settings.ui.screens.sync.SyncScreenViewModel

@Module
internal interface SettingsViewModelModule {

    @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
    fun bindSettingsViewModel(viewModel: SettingsViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(PrimaryColorScreenViewModel::class)]
    fun bindPrimaryColorScreenViewModel(viewModel: PrimaryColorScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(SyncScreenViewModel::class)]
    fun bindSyncScreenViewModel(viewModel: SyncScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(AboutScreenViewModel::class)]
    fun bindAboutScreenViewModel(viewModel: AboutScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(LanguageScreenViewModel::class)]
    fun bindLanguageScreenViewModel(viewModel: LanguageScreenViewModel):ViewModel
}