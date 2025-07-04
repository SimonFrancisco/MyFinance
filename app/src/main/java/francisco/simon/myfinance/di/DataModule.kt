package francisco.simon.myfinance.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import francisco.simon.myfinance.data.data_source.network.api.ApiFactory
import francisco.simon.myfinance.data.data_source.network.api.ApiService
import francisco.simon.myfinance.data.data_source.local.db.CategoriesDao
import francisco.simon.myfinance.data.data_source.local.db.CategoryDatabase
import francisco.simon.myfinance.data.repositories.AccountRepositoryImpl
import francisco.simon.myfinance.data.repositories.CategoryRepositoryImpl
import francisco.simon.myfinance.data.repositories.TransactionRepositoryImpl
import francisco.simon.myfinance.domain.repository.AccountRepository
import francisco.simon.myfinance.domain.repository.CategoryRepository
import francisco.simon.myfinance.domain.repository.TransactionRepository
import javax.inject.Singleton

/**
 * Hilt module that binds repositories to their implementations and
 * provides implementations for object creation. Singleton is used.
 * @author Simon Francisco
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @[Singleton Binds]
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @[Singleton Binds]
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @[Singleton Binds]
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    companion object {
        @[Singleton Provides]
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @[Singleton Provides]
        fun provideCategoryDatabase(@ApplicationContext context: Context): CategoryDatabase {
            return CategoryDatabase.getInstance(context)
        }

        @[Singleton Provides]
        fun provideCategoriesDao(database: CategoryDatabase): CategoriesDao {
            return database.categoryDao()
        }
    }

}