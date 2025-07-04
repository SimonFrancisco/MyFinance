package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): Result<List<Category>, Error>

    fun searchCategories(query: String): Flow<List<Category>>
}