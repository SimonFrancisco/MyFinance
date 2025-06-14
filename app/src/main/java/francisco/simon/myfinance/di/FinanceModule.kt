package francisco.simon.myfinance.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import francisco.simon.myfinance.data.CategoryRepositoryImpl
import francisco.simon.myfinance.data.TransactionRepositoryImpl
import francisco.simon.myfinance.domain.repository.CategoryRepository
import francisco.simon.myfinance.domain.repository.TransactionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface FinanceModule {

    @[Singleton Binds]
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @[Singleton Binds]
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

}