package francisco.simon.myfinance.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.repository.CategoryRepository
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.feature.account.di.AccountDependencies
import francisco.simon.feature.category.di.CategoryDependencies
import francisco.simon.feature.expenses.di.ExpensesDependencies
import francisco.simon.feature.income.di.IncomeDependencies
import francisco.simon.myfinance.App

@ApplicationScope
@Component(modules = [DataModule::class, WorkerModule::class])
internal interface ApplicationComponent :
    CategoryDependencies,
    AccountDependencies,
    IncomeDependencies,
    ExpensesDependencies {

    override fun getTransactionsRepository(): TransactionRepository

    override fun getAccountRepository(): AccountRepository

    override fun getCategoryRepository(): CategoryRepository

    fun inject(application:App)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}