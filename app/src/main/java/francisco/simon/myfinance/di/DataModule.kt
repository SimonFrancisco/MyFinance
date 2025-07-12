package francisco.simon.myfinance.di

import android.content.Context
import dagger.Module
import dagger.Provides
import francisco.simon.core.data.data_source.local.db.CategoriesDao
import francisco.simon.core.data.data_source.local.db.CategoryDatabase
import francisco.simon.core.data.data_source.network.api.ApiClient
import francisco.simon.core.data.data_source.network.api.ApiFactory
import francisco.simon.core.data.data_source.network.api.ApiService
import francisco.simon.core.data.repositories.AccountRepositoryImpl
import francisco.simon.core.data.repositories.CategoryRepositoryImpl
import francisco.simon.core.data.repositories.TransactionRepositoryImpl
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.repository.CategoryRepository
import francisco.simon.core.domain.repository.TransactionRepository

/**
 * Dagger module that binds repositories to their implementations and
 * provides implementations for object creation. Singleton is used.
 * @author Simon Francisco
 */
@Module
internal object DataModule {

    @[ApplicationScope Provides]
    fun provideTransactionRepository(
        apiService: ApiService,
        apiClient: ApiClient
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            apiService = apiService,
            apiClient = apiClient
        )
    }

    @[ApplicationScope Provides]
    fun providesCategoryRepository(
        apiService: ApiService,
        apiClient: ApiClient,
        categoriesDao: CategoriesDao
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            apiService = apiService,
            apiClient = apiClient,
            categoriesDao = categoriesDao
        )
    }
    @[ApplicationScope Provides]
    fun providesAccountRepository(
        apiService: ApiService,
        apiClient: ApiClient
    ): AccountRepository {
        return AccountRepositoryImpl(
            apiService = apiService,
            apiClient = apiClient
        )
    }

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

    @[ApplicationScope Provides]
    fun provideApiClient(context: Context): ApiClient {
        return ApiClient(context)
    }

}