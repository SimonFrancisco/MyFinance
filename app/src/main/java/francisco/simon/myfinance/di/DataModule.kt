package francisco.simon.myfinance.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import francisco.simon.myfinance.data.data_source.local.db.CategoriesDao
import francisco.simon.myfinance.data.data_source.local.db.CategoryDatabase
import francisco.simon.myfinance.data.data_source.network.api.ApiFactory
import francisco.simon.myfinance.data.data_source.network.api.ApiService
import francisco.simon.myfinance.data.repositories.AccountRepositoryImpl
import francisco.simon.myfinance.data.repositories.CategoryRepositoryImpl
import francisco.simon.myfinance.data.repositories.TransactionRepositoryImpl
import francisco.simon.myfinance.domain.repository.AccountRepository
import francisco.simon.myfinance.domain.repository.CategoryRepository
import francisco.simon.myfinance.domain.repository.TransactionRepository

/**
 * Hilt module that binds repositories to their implementations and
 * provides implementations for object creation. Singleton is used.
 * @author Simon Francisco
 */
@Module
internal interface DataModule {

    @[ApplicationScope Binds]
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @[ApplicationScope Binds]
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @[ApplicationScope Binds]
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @[ApplicationScope Provides]
        fun provideCategoryDatabase(context: Context): CategoryDatabase {
            return CategoryDatabase.getInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideCategoriesDao(database: CategoryDatabase): CategoriesDao {
            return database.categoryDao()
        }
    }

}