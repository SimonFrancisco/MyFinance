package francisco.simon.myfinance.data.repositories

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.core.domain.utils.map
import francisco.simon.myfinance.data.api.ApiClient
import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.data.mappers.toCategoryList
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of Category repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 * @author Simon Francisco
 */
class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiClient: ApiClient,
) : CategoryRepository {

    override suspend fun getAllCategories(): Result<List<Category>, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall { apiService.getCategories() }.map { listCategoriesDto ->
                listCategoriesDto.toCategoryList()
            }
        }
    }
}