package francisco.simon.myfinance.ui.features.expense.screens.expense

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.expense.model.ExpenseUI

/**
 * All Expense screen states
 * @author Simon Francisco
 */
sealed class ExpenseScreenState {
    data object Loading : ExpenseScreenState()
    data class Success(val expens: List<ExpenseUI>) : ExpenseScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : ExpenseScreenState()
}