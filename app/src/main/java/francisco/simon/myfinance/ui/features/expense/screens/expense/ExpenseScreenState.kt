package francisco.simon.myfinance.ui.features.expense.screens.expense

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.expense.model.Expense

sealed class ExpenseScreenState {
    data object Loading : ExpenseScreenState()
    data class Success(val expenses: List<Expense>) : ExpenseScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : ExpenseScreenState()
}