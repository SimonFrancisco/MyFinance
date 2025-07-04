package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(query: String): Flow<List<Category>> {
        return repository.searchCategories(query)
    }
}