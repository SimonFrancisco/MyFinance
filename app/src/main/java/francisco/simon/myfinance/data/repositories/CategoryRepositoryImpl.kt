package francisco.simon.myfinance.data.repositories

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.core.domain.utils.map
import francisco.simon.myfinance.data.data_source.local.db.CategoriesDao
import francisco.simon.myfinance.data.data_source.network.api.ApiClient
import francisco.simon.myfinance.data.data_source.network.api.ApiService
import francisco.simon.myfinance.data.data_source.toCategoryDbModel
import francisco.simon.myfinance.data.data_source.toListCategory
import francisco.simon.myfinance.data.mappers.toCategoryList
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of Category repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 * @param categoriesDao
 * @author Simon Francisco
 */
class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiClient: ApiClient,
    private val categoriesDao: CategoriesDao,
) : CategoryRepository {

    override suspend fun getAllCategories(): Result<List<Category>, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall { apiService.getCategories() }.map { listCategoriesDto ->
                listCategoriesDto.map { categoryDto ->
                    categoriesDao.insertCategory(categoryDto.toCategoryDbModel())
                }
                listCategoriesDto.toCategoryList()
            }
        }
    }

    override fun searchCategories(query: String): Flow<List<Category>> {
        return categoriesDao.searchCategory(query).map {
            it.toListCategory()
        }.flowOn(Dispatchers.IO)
    }
}