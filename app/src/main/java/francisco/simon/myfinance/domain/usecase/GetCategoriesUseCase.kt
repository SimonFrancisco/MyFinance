package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
     operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}