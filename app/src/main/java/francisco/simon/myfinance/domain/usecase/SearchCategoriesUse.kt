package francisco.simon.myfinance.domain.usecase

import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(query: String): Flow<List<Category>> {
        return repository.searchCategories(query)
    }
}