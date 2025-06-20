package francisco.simon.myfinance.ui.features.category.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.usecase.GetCategoriesUseCase
import francisco.simon.myfinance.ui.features.category.mapper.toListCategoryUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<CategoryScreenState> =
        MutableStateFlow(CategoryScreenState.Loading)
    val state = _state

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            delay(2000)
            getCategoriesUseCase().collect {
                _state.value = CategoryScreenState.Success(it.toListCategoryUI())
            }
        }
    }
}