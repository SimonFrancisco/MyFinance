package francisco.simon.feature.category.di

import androidx.lifecycle.ViewModelProvider
import dagger.Component

@CategoryScope
@Component(dependencies = [CategoryDependencies::class])
interface CategoryComponent {
    fun getCategoryViewModelFactory(): ViewModelProvider.Factory
}


