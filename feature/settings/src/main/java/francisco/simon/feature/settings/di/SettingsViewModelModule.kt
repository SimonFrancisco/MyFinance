package francisco.simon.feature.settings.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.settings.ui.screens.settings.SettingsViewModel
import francisco.simon.feature.settings.ui.screens.sync.SyncScreenViewModel

@Module
internal interface SettingsViewModelModule {

    @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
    fun bindSettingsViewModel(viewModel: SettingsViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(SyncScreenViewModel::class)]
    fun bindSyncScreenViewModel(viewModel: SyncScreenViewModel):ViewModel
}