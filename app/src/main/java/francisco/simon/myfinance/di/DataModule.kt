package francisco.simon.myfinance.di

import android.content.Context
import dagger.Module
import dagger.Provides
import francisco.simon.core.data.local.MyFinanceDatabase
import francisco.simon.core.data.local.account.db.AccountDao
import francisco.simon.core.data.local.category.db.CategoriesDao
import francisco.simon.core.data.local.transactions.db.TransactionDao
import francisco.simon.core.data.network.api.ApiClient
import francisco.simon.core.data.network.api.ApiFactory
import francisco.simon.core.data.network.api.ApiService
import francisco.simon.core.data.network.preferences.SyncPreferencesImpl
import francisco.simon.core.data.network.repositories.AccountRepositoryImpl
import francisco.simon.core.data.network.repositories.CategoryRepositoryImpl
import francisco.simon.core.data.network.repositories.TransactionRepositoryImpl
import francisco.simon.core.domain.preferences.SyncPreferences
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
        apiClient: ApiClient,
        categoriesDao: CategoriesDao,
        accountDao: AccountDao,
        transactionDao: TransactionDao,
        syncPreferences: SyncPreferences
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            apiService = apiService,
            apiClient = apiClient,
            categoriesDao = categoriesDao,
            accountDao = accountDao,
            transactionDao = transactionDao,
            syncPreferences = syncPreferences
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
        apiClient: ApiClient,
        accountDao: AccountDao,
        transactionDao: TransactionDao
    ): AccountRepository {
        return AccountRepositoryImpl(
            apiService = apiService,
            apiClient = apiClient,
            accountDao = accountDao,
            transactionDao = transactionDao
        )
    }

    @[ApplicationScope Provides]
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

    @[ApplicationScope Provides]
    fun provideMyFinanceDatabase(context: Context): MyFinanceDatabase {
        return MyFinanceDatabase.getInstance(context)
    }

    @[ApplicationScope Provides]
    fun provideCategoriesDao(database: MyFinanceDatabase): CategoriesDao {
        return database.categoryDao()
    }

    @[ApplicationScope Provides]
    fun provideAccountDao(database: MyFinanceDatabase): AccountDao {
        return database.accountDao()
    }

    @[ApplicationScope Provides]
    fun provideTransactionDao(database: MyFinanceDatabase): TransactionDao {
        return database.transactionDao()
    }

    @[ApplicationScope Provides]
    fun provideApiClient(context: Context): ApiClient {
        return ApiClient(context)
    }

    @[ApplicationScope Provides]
    fun provideSyncPreferences(context: Context): SyncPreferences {
        return SyncPreferencesImpl(context)
    }

}