package francisco.simon.feature.expenses.di

import dagger.Component
import francisco.simon.feature.expenses.ViewModelFactory

@ExpensesScope
@Component(modules = [ExpensesViewModelModule::class],
    dependencies = [ExpensesDependencies::class])
internal interface ExpensesComponent {
    fun getViewModelFactory(): ViewModelFactory

}