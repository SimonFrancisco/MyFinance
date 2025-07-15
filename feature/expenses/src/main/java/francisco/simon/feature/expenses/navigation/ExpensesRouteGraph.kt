package francisco.simon.feature.expenses.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ExpenseGraph {
    @Serializable
    data object ExpenseRoute

    @Serializable
    data object ExpensesHistoryRoute

    @Serializable
    data object AddExpenseRoute

    @Serializable
    data class EditExpenseRoute(val expenseId: Int)

}
