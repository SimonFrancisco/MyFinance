package francisco.simon.feature.account.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.account.ui.screens.account.AccountViewModel

@Module
internal interface AccountViewModelModule {
    @[Binds IntoMap ViewModelKey(AccountViewModel::class)]
    fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel
}