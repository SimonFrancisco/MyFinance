package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAllCategories(): Flow<List<Category>>
}