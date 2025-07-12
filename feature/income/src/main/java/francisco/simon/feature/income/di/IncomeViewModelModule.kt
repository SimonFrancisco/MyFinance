package francisco.simon.feature.income.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.income.ui.screens.history.IncomeHistoryScreenViewModel
import francisco.simon.feature.income.ui.screens.income.IncomeScreenViewModel

@Module
internal interface IncomeViewModelModule {

    @[Binds IntoMap ViewModelKey(IncomeScreenViewModel::class)]
    fun bindIncomeViewModel(viewModel: IncomeScreenViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(IncomeHistoryScreenViewModel::class)]
    fun bindIncomeHistory(viewModel: IncomeHistoryScreenViewModel):ViewModel

}