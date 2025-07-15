package francisco.simon.feature.expenses.di

import dagger.BindsInstance
import dagger.Subcomponent
import francisco.simon.feature.expenses.ViewModelFactory

@Subcomponent(modules = [EditExpenseViewModelModule::class])
internal interface ExpenseEditSubComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @[BindsInstance TransactionIdQualifier] transactionId: Int
        ): ExpenseEditSubComponent
    }
}