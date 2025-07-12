package francisco.simon.core.data.repositories

import francisco.simon.core.data.data_source.local.db.CategoriesDao
import francisco.simon.core.data.data_source.network.api.ApiClient
import francisco.simon.core.data.data_source.network.api.ApiService
import francisco.simon.core.data.data_source.toCategoryDbModel
import francisco.simon.core.data.data_source.toListCategory
import francisco.simon.core.data.mappers.toCategoryList
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.repository.CategoryRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Implementation of Category repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 * @param categoriesDao
 * @author Simon Francisco
 */
class CategoryRepositoryImpl(
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