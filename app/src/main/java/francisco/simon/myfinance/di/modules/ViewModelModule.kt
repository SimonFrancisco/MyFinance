package francisco.simon.myfinance.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.myfinance.MyFinanceViewModel
import francisco.simon.myfinance.di.key.ViewModelKey

@Module
interface ViewModelModule {

    @[Binds IntoMap ViewModelKey(MyFinanceViewModel::class)]
    fun bindMyFinanceViewModel(viewModel: MyFinanceViewModel): ViewModel
}