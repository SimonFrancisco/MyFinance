package francisco.simon.core.domain.repository

import francisco.simon.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
interface CategoryRepository {
    suspend fun getAllCategories(): Result<List<Category>, Error>

    fun searchCategories(query: String): Flow<List<Category>>
}