package francisco.simon.feature.category.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.onError
import francisco.simon.core.domain.utils.onSuccess
import francisco.simon.core.ui.utils.toStringRes
import francisco.simon.feature.category.domain.GetCategoriesUseCase
import francisco.simon.feature.category.domain.SearchCategoriesUseCase
import francisco.simon.feature.category.ui.mapper.toListCategoryUI
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO migrate to complete offline work
/**
 * ViewModel for categories, works with state
 * @param getCategoriesUseCase
 * @param searchCategoriesUseCase
 * @author Simon Francisco
 */
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