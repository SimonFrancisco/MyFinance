package francisco.simon.myfinance.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.navigation.ExpenseGraph
import francisco.simon.myfinance.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.myfinance.navigation.ExpenseGraph.ExpensesHistoryRoute
import francisco.simon.myfinance.ui.features.expense.screens.expense.ExpenseScreen
import francisco.simon.myfinance.ui.features.expense.screens.history.ExpensesHistoryScreen

/**
 * Extension function for Expense nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.expenseNavGraph() {
    navigation<ExpenseGraph>(startDestination = ExpenseRoute) {
        composable<ExpenseRoute> {
            ExpenseScreen()
        }
        composable<ExpensesHistoryRoute> {
            ExpensesHistoryScreen()
        }
    }
}