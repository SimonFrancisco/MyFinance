package francisco.simon.myfinance.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.category.ui.screens.CategoryViewModel
import francisco.simon.myfinance.ui.features.expense.screens.expense.ExpenseScreenViewModel
import francisco.simon.myfinance.ui.features.expense.screens.history.ExpensesHistoryScreenViewModel
import francisco.simon.myfinance.ui.features.income.screens.history.IncomeHistoryScreenViewModel
import francisco.simon.myfinance.ui.features.income.screens.income.IncomeScreenViewModel
import francisco.simon.myfinance.ui.features.settings.screens.settings.SettingsViewModel

@Module
interface ViewModelModule {

    @[Binds IntoMap ViewModelKey(IncomeScreenViewModel::class)]
    fun binIncomeScreenViewModel(viewModel: IncomeScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(IncomeHistoryScreenViewModel::class)]
    fun bindIncomeHistoryScreenViewModel(viewModel: IncomeHistoryScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(ExpenseScreenViewModel::class)]
    fun bindExpenseScreenViewModel(viewModel: ExpenseScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(ExpensesHistoryScreenViewModel::class)]
    fun bindExpensesHistoryScreenViewModel(viewModel: ExpensesHistoryScreenViewModel):ViewModel


    @[Binds IntoMap ViewModelKey(CategoryViewModel::class)]
    fun bindCategoryViewModel(viewModel: CategoryViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
    fun bindSettingsViewModel(viewModel: SettingsViewModel):ViewModel



}