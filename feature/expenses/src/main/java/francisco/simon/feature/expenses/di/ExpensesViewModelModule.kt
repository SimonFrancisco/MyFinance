package francisco.simon.feature.expenses.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.expenses.ui.screens.add_expense.AddExpenseScreenViewModel
import francisco.simon.feature.expenses.ui.screens.analysis.AnalysisExpensesScreenViewModel
import francisco.simon.feature.expenses.ui.screens.expense.ExpenseScreenViewModel
import francisco.simon.feature.expenses.ui.screens.history.ExpensesHistoryScreenViewModel

@Module
internal interface ExpensesViewModelModule {

    @[Binds IntoMap ViewModelKey(ExpenseScreenViewModel::class)]
    fun bindExpensesViewModel(viewModel: ExpenseScreenViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(ExpensesHistoryScreenViewModel::class)]
    fun bindExpensesHistory(viewModel: ExpensesHistoryScreenViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(AddExpenseScreenViewModel::class)]
    fun bindAddExpenseViewModel(viewModel: AddExpenseScreenViewModel):ViewModel

    @[Binds IntoMap ViewModelKey(AnalysisExpensesScreenViewModel::class)]
    fun bindAnalysisExpensesScreenViewModel(viewModel: AnalysisExpensesScreenViewModel):ViewModel

}