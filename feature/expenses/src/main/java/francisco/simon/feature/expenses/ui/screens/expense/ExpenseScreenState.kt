package francisco.simon.feature.expenses.ui.screens.expense

import androidx.annotation.StringRes
import francisco.simon.feature.expenses.ui.model.ExpenseUI

/**
 * All Expense screen states
 * @author Simon Francisco
 */
internal sealed class ExpenseScreenState {
    data object Loading : ExpenseScreenState()
    data class Success(val expenses: List<ExpenseUI>) : ExpenseScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : ExpenseScreenState()
}