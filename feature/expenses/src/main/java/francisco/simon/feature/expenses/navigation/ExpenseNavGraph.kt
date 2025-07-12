package francisco.simon.feature.expenses.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.expenses.ui.screens.expense.ExpenseScreen
import francisco.simon.feature.expenses.ui.screens.history.ExpensesHistoryScreen

/**
 * Extension function for Expense nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.expenseNavGraph(
    appBarState: MutableState<AppBarState>,
    navController: NavController
) {
    navigation<ExpenseGraph>(startDestination = ExpenseGraph.ExpenseRoute) {
        composable<ExpenseGraph.ExpenseRoute> {
            ExpenseScreen(
                appBarState = appBarState,
                onGoToHistoryScreen = {
                    navController.navigate(ExpenseGraph.ExpensesHistoryRoute)
                }
            )
        }
        composable<ExpenseGraph.ExpensesHistoryRoute> {
            ExpensesHistoryScreen(
                appBarState = appBarState,
                onGoBackToExpensesScreen = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}