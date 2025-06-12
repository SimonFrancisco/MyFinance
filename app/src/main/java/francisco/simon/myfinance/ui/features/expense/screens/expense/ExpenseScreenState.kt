package francisco.simon.myfinance.ui.features.expense.screens.expense

import francisco.simon.myfinance.ui.features.expense.model.Expense

sealed class ExpenseScreenState {
    data object Loading : ExpenseScreenState()
    data class Success(val expenses: List<Expense>) : ExpenseScreenState()
    data class Error(val errorMessage: String) : ExpenseScreenState()
}