package francisco.simon.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import francisco.simon.core.data.local.account.db.AccountDao
import francisco.simon.core.data.local.account.model.AccountDbModel
import francisco.simon.core.data.local.category.db.CategoriesDao
import francisco.simon.core.data.local.category.model.CategoryDbModel

/**
 * This a general database, don't pay attention to the name, it was created initially only for categories
 */
@Database(
    entities = [CategoryDbModel::class, AccountDbModel::class],
    version = 2,
    exportSchema = false
)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoriesDao

    abstract fun accountDao(): AccountDao

    companion object {
        private const val DB_NAME = "CategoryDatabase"
        private var INSTANCE: CategoryDatabase? = null
        private val LOCK = Any()

        fun getInstance(
            context: Context
        ): CategoryDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val database = Room.databaseBuilder(
                    context = context,
                    klass = CategoryDatabase::class.java,
                    name = DB_NAME
                ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = database
                return database
            }
        }
    }
}