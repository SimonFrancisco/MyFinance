package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.utils.Error
import francisco.simon.myfinance.domain.utils.Result

interface CategoryRepository {

    suspend fun getAllCategories(): Result<List<Category>, Error>
}