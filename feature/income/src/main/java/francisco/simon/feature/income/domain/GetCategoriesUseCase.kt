package francisco.simon.feature.income.domain

import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.repository.CategoryRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import javax.inject.Inject

internal class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<Category>, Error> {
        return repository.getCategoriesByType(true)
    }
}