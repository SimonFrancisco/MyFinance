package francisco.simon.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import francisco.simon.core.data.local.account.db.AccountDao
import francisco.simon.core.data.local.account.model.AccountDbModel
import francisco.simon.core.data.local.category.db.CategoriesDao
import francisco.simon.core.data.local.category.model.CategoryDbModel
import francisco.simon.core.data.local.transactions.db.TransactionDao
import francisco.simon.core.data.local.transactions.model.TransactionDbModel


@Database(
    entities = [CategoryDbModel::class, AccountDbModel::class,TransactionDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class MyFinanceDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoriesDao

    abstract fun accountDao(): AccountDao

    abstract fun transactionDao():TransactionDao

    companion object {
        private const val DB_NAME = "MyFinanceDatabase"
        private var INSTANCE: MyFinanceDatabase? = null
        private val LOCK = Any()

        fun getInstance(
            context: Context
        ): MyFinanceDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val database = Room.databaseBuilder(
                    context = context,
                    klass = MyFinanceDatabase::class.java,
                    name = DB_NAME
                ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = database
                return database
            }
        }
    }
}