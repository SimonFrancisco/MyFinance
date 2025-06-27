package francisco.simon.myfinance.ui.features.category.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.NetworkError
import francisco.simon.myfinance.core.domain.utils.onError
import francisco.simon.myfinance.core.domain.utils.onSuccess
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.usecase.GetCategoriesUseCase
import francisco.simon.myfinance.ui.features.category.mapper.toListCategoryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for categories, works with state
 * @param getCategoriesUseCase
 * @author Simon Francisco
 */
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
        updateLoading()
        viewModelScope.launch {
            getCategoriesUseCase().onSuccess { categories ->
                updateSuccess(categories)
            }.onError { error ->
                updateError(error)
            }

        }
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            CategoryScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateSuccess(categories: List<Category>) {
        _state.update {
            CategoryScreenState.Success(categories = categories.toListCategoryUI())
        }
    }

    private fun updateLoading() {
        _state.update {
            CategoryScreenState.Loading
        }
    }

    fun retry() {
        loadCategories()
    }
}