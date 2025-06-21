package francisco.simon.myfinance.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import francisco.simon.myfinance.data.repositories.CategoryRepositoryImpl
import francisco.simon.myfinance.data.repositories.TransactionRepositoryImpl
import francisco.simon.myfinance.data.api.ApiFactory
import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.domain.repository.CategoryRepository
import francisco.simon.myfinance.domain.repository.TransactionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @[Singleton Binds]
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @[Singleton Binds]
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    companion object {
        @[Singleton Provides]
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }

}