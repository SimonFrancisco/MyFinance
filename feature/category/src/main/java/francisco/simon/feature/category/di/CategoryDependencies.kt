package francisco.simon.feature.category.di

import francisco.simon.core.domain.repository.CategoryRepository

interface CategoryDependencies {
    fun getCategoryRepository(): CategoryRepository
}