package francisco.simon.myfinance.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import francisco.simon.myfinance.data.data_source.local.model.CategoryDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories ORDER BY id ASC")
    fun getCategories(): Flow<List<CategoryDbModel>>

    @Query("SELECT * FROM categories WHERE name LIKE:query ORDER BY id ASC")
    fun searchCategory(query: String): Flow<List<CategoryDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryDbModel: CategoryDbModel)

    @Query("DELETE FROM categories")
    suspend fun clearCategories()
}