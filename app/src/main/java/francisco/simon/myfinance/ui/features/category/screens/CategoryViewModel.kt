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
import francisco.simon.myfinance.domain.usecase.SearchCategoriesUseCase
import francisco.simon.myfinance.ui.features.category.mapper.toListCategoryUI
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for categories, works with state
 * @param getCategoriesUseCase
 * @param searchCategoriesUseCase
 * @author Simon Francisco
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state: MutableStateFlow<CategoryScreenState> =
        MutableStateFlow(CategoryScreenState.Loading)
    val state: StateFlow<CategoryScreenState> = _state

    private lateinit var categoriesList: List<Category>


    init {
        loadCategories()
    }

    private fun loadCategories() {
        updateLoading()
        viewModelScope.launch {
            getCategoriesUseCase().onSuccess { categories ->
                categoriesList = categories
                updateSuccess(categories)
            }.onError { error ->
                updateError(error)
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun searchCategory(query: String) {
        updateLoading()
        val queryRoom = "%${query}%"
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (query.isNotEmpty()) {
                searchCategoriesUseCase(queryRoom)
                    .debounce(DEBOUNCE)
                    .collectLatest { categories ->
                        updateSuccess(categories)
                    }
            } else {
                loadCategories()
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

    companion object {
        private const val DEBOUNCE = 2000L
    }
}