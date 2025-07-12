package francisco.simon.feature.category.domain

import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(query: String): Flow<List<Category>> {
        return repository.searchCategories(query)
    }
}