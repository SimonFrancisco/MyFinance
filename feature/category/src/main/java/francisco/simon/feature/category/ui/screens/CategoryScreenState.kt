package francisco.simon.feature.category.ui.screens

import androidx.annotation.StringRes
import francisco.simon.feature.category.ui.model.CategoryUI

/**
 * All Category screen states
 * @author Simon Francisco
 */
sealed class CategoryScreenState {
    data object Loading : CategoryScreenState()
    data class Success(val categories: List<CategoryUI>) : CategoryScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : CategoryScreenState()
}