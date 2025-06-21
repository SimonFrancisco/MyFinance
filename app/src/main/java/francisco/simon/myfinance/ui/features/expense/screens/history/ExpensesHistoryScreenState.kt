package francisco.simon.myfinance.ui.features.expense.screens.history

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.ui.features.expense.model.Expense

sealed class ExpensesHistoryScreenState {
    data object Loading : ExpensesHistoryScreenState()
    data class Success(val expenses: List<Transaction>) : ExpensesHistoryScreenState()
    data class Error(val errorMessage: String) : ExpensesHistoryScreenState()
}