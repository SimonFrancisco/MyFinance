package francisco.simon.myfinance.ui.features.category.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.usecase.GetCategoriesUseCase
import francisco.simon.myfinance.ui.features.category.mapper.toListCategoryUI
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    val state = getCategoriesUseCase().map {
        CategoryScreenState.Success(it.toListCategoryUI())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CategoryScreenState.Loading
    )
}