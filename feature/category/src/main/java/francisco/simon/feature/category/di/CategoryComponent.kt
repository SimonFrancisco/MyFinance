package francisco.simon.feature.category.di

import dagger.Component
import francisco.simon.feature.category.ViewModelFactory

@CategoryScope
@Component(modules = [CategoryViewModelModule::class],dependencies = [CategoryDependencies::class])
internal interface CategoryComponent {
    fun getCategoryViewModelFactory(): ViewModelFactory
}


