package francisco.simon.feature.category.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.category.ui.screens.CategoryViewModel

@Module
internal interface CategoryViewModelModule {

    @[Binds IntoMap ViewModelKey(CategoryViewModel::class)]
    fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel

}