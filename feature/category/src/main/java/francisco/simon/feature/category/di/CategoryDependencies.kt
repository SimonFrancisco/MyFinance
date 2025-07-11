package francisco.simon.feature.category.di

import androidx.lifecycle.ViewModelProvider
import francisco.simon.core.domain.repository.CategoryRepository

interface CategoryDependencies {
    fun getCategoryRepository(): CategoryRepository
    fun getViewModelFactoryCategory(): ViewModelProvider.Factory
}