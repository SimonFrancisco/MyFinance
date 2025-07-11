package francisco.simon.myfinance.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.myfinance.ui.features.settings.screens.settings.SettingsViewModel

@Module
interface ViewModelModule {
    @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
    fun bindSettingsViewModel(viewModel: SettingsViewModel):ViewModel

}