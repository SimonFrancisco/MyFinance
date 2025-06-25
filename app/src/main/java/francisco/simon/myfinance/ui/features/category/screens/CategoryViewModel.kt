package francisco.simon.myfinance.ui.features.category.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.usecase.GetCategoriesUseCase
import francisco.simon.myfinance.domain.utils.onError
import francisco.simon.myfinance.domain.utils.onSuccess
import francisco.simon.myfinance.ui.features.category.mapper.toListCategoryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CategoryScreenState> =
        MutableStateFlow(CategoryScreenState.Loading)
    val state: StateFlow<CategoryScreenState> = _state

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _state.update {
            CategoryScreenState.Loading
        }
        viewModelScope.launch {
            getCategoriesUseCase().onSuccess { categories ->
                _state.update {
                    CategoryScreenState.Success(categories = categories.toListCategoryUI())
                }
            }.onError { error ->
                val errorRes = error.toStringRes()
                _state.update {
                    CategoryScreenState.Error(errorMessageRes = errorRes)
                }
            }

        }
    }

    fun retry() {
        loadCategories()
    }
}