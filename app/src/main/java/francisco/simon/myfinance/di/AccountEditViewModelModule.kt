package francisco.simon.myfinance.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.myfinance.ui.features.account.screens.edit.AccountEditViewModel

@Module
interface AccountEditViewModelModule{
    @[Binds IntoMap ViewModelKey(AccountEditViewModel::class)]
    fun bindAccountEditViewModel(viewModel: AccountEditViewModel):ViewModel
}