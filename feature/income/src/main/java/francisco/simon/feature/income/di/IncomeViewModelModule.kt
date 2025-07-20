package francisco.simon.feature.income.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.income.ui.screens.add_income.AddIncomeScreenViewModel
import francisco.simon.feature.income.ui.screens.analysis.AnalysisIncomeScreenViewModel
import francisco.simon.feature.income.ui.screens.history.IncomeHistoryScreenViewModel
import francisco.simon.feature.income.ui.screens.income.IncomeScreenViewModel

@Module
internal interface IncomeViewModelModule {

    @[Binds IntoMap ViewModelKey(IncomeScreenViewModel::class)]
    fun bindIncomeViewModel(viewModel: IncomeScreenViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(IncomeHistoryScreenViewModel::class)]
    fun bindIncomeHistoryViewModel(viewModel: IncomeHistoryScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(AddIncomeScreenViewModel::class)]
    fun bindAddIncomeViewModel(viewModel: AddIncomeScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(AnalysisIncomeScreenViewModel::class)]
    fun bindAnalysisIncomeScreenViewModel(viewModel: AnalysisIncomeScreenViewModel):ViewModel
}