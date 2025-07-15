package francisco.simon.feature.income.di

import dagger.BindsInstance
import dagger.Subcomponent
import francisco.simon.feature.income.ViewModelFactory

@Subcomponent(modules = [EditIncomeViewModelModule::class])
internal interface IncomeEditSubComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @[BindsInstance TransactionIdQualifier] transactionId: Int
        ): IncomeEditSubComponent
    }
}