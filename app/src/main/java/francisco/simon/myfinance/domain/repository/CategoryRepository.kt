package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.domain.entity.Category

interface CategoryRepository {

    suspend fun getAllCategories(): Result<List<Category>, Error>
}