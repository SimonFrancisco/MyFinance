package francisco.simon.feature.account.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.account.ui.screens.edit.AccountEditViewModel

@Module
internal interface EditAccountViewModelModule {
    @[Binds IntoMap ViewModelKey(AccountEditViewModel::class)]
    fun bindAccountEditViewModel(viewModel: AccountEditViewModel): ViewModel
}