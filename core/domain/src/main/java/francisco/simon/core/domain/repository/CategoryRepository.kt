package francisco.simon.core.domain.repository

import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): Result<List<Category>, Error>

    fun searchCategories(query: String): Flow<List<Category>>
}