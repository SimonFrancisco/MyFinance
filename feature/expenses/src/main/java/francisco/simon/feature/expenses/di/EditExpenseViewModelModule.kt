package francisco.simon.feature.expenses.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.expenses.ui.screens.edit_expense.EditExpenseScreenViewModel

@Module
internal interface EditExpenseViewModelModule {
    @[Binds IntoMap ViewModelKey(EditExpenseScreenViewModel::class)]
    fun bindEditExpenseScreenViewModel(viewModel: EditExpenseScreenViewModel): ViewModel
}