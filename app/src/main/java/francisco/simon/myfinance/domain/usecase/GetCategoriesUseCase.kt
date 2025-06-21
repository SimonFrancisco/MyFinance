package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import francisco.simon.myfinance.domain.utils.NetworkResult
import javax.inject.Inject


class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Category>> {
        return repository.getAllCategories()
    }
}