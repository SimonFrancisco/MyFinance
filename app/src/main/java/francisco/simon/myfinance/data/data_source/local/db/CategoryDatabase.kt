package francisco.simon.myfinance.data.data_source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import francisco.simon.myfinance.data.data_source.local.model.CategoryDbModel

@Database(entities = [CategoryDbModel::class], version = 1, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoriesDao

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
            }
            val database = Room.databaseBuilder(
                context = context,
                klass = CategoryDatabase::class.java,
                name = DB_NAME
            ).build()
            INSTANCE = database
            return database
        }
    }
}