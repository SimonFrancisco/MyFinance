package francisco.simon.myfinance.ui.features.category.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.usecase.GetCategoriesUseCase
import francisco.simon.myfinance.domain.utils.NetworkResult
import francisco.simon.myfinance.ui.features.category.mapper.toListCategoryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
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
            when (val categories = getCategoriesUseCase()) {
                is NetworkResult.Error -> {
                    _state.update {
                        CategoryScreenState.Error(categories.message)
                    }
                }

                is NetworkResult.Exception -> {
                    _state.update {
                        CategoryScreenState.Error(categories.e.message.toString())

                    }
                }

                is NetworkResult.Success<List<Category>> -> {
                    _state.update {
                        CategoryScreenState.Success(categories = categories.data.toListCategoryUI())

                    }
                }
            }
        }
    }

    fun retry() {
        loadCategories()
    }
}