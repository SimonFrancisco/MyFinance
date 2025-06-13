package francisco.simon.myfinance.ui.features.category.screens

import francisco.simon.myfinance.ui.features.category.model.CategoryUI

sealed class CategoryScreenState {
    data object Loading : CategoryScreenState()
    data class Success(val categories: List<CategoryUI>) : CategoryScreenState()
    data class Error(val message: String) : CategoryScreenState()
}