package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.utils.NetworkResult

interface CategoryRepository {

    suspend fun getAllCategories(): NetworkResult<List<Category>>
}