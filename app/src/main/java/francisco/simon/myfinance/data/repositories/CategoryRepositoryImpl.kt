package francisco.simon.myfinance.data.repositories

import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.data.api.safeApiCall
import francisco.simon.myfinance.data.mappers.toCategory
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import francisco.simon.myfinance.domain.utils.NetworkResult
import francisco.simon.myfinance.domain.utils.flatMapIfSuccess
import francisco.simon.myfinance.domain.utils.toSuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    override suspend fun getAllCategories(): NetworkResult<List<Category>> {
        return withContext(Dispatchers.IO) {
            safeApiCall { apiService.getCategories() }
                .flatMapIfSuccess { dtoList ->
                    dtoList.map {
                        it.toCategory()
                    }.toSuccessResult()
                }
        }

    }
}