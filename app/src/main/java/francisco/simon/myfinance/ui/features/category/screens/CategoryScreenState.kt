package francisco.simon.myfinance.ui.features.category.screens

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.category.model.CategoryUI

sealed class CategoryScreenState {
    data object Loading : CategoryScreenState()
    data class Success(val categories: List<CategoryUI>) : CategoryScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : CategoryScreenState()
}