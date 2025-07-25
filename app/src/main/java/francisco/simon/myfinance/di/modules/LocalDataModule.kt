package francisco.simon.myfinance.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import francisco.simon.core.data.local.MyFinanceDatabase
import francisco.simon.core.data.local.account.db.AccountDao
import francisco.simon.core.data.local.category.db.CategoriesDao
import francisco.simon.core.data.local.transactions.db.TransactionDao
import francisco.simon.myfinance.di.ApplicationScope

@Module
internal object LocalDataModule {

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
}