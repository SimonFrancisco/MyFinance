package francisco.simon.feature.expenses.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.expenses.navigation.ExpenseGraph.AddExpenseRoute
import francisco.simon.feature.expenses.navigation.ExpenseGraph.AnalysisExpenseRoute
import francisco.simon.feature.expenses.navigation.ExpenseGraph.EditExpenseRoute
import francisco.simon.feature.expenses.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.feature.expenses.navigation.ExpenseGraph.ExpensesHistoryRoute
import francisco.simon.feature.expenses.ui.screens.add_expense.AddExpenseScreen
import francisco.simon.feature.expenses.ui.screens.analysis.AnalysisExpenseScreen
import francisco.simon.feature.expenses.ui.screens.expense.ExpenseScreen
import francisco.simon.feature.expenses.ui.screens.expense.onGoToAddExpenseScreenGlobal
import francisco.simon.feature.expenses.ui.screens.history.ExpensesHistoryScreen
import francisco.simon.feature.income.ui.screens.edit_income.EditExpenseScreen

/**
 * Extension function for Expense nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.expenseNavGraph(
    appBarState: MutableState<AppBarState>,
    navController: NavController
) {
    navigation<ExpenseGraph>(startDestination = ExpenseRoute) {
        composable<ExpenseRoute> {
            ExpenseScreen(
                appBarState = appBarState,
                onGoToHistoryScreen = {
                    navController.navigate(ExpensesHistoryRoute)
                },
                onGoToAddExpenseScreen = {
                    navController.navigate(AddExpenseRoute)
                    onGoToAddExpenseScreenGlobal = null
                }, onGoToEditExpenseScreen = { transactionId ->
                    navController.navigate(EditExpenseRoute(transactionId))
                }
            )
        }
        composable<ExpensesHistoryRoute> {
            ExpensesHistoryScreen(
                appBarState = appBarState,
                onGoBackToExpensesScreen = {
                    navController.safePopBackStack()
                },
                onGoToEditExpenseScreen = { transactionId ->
                    navController.navigate(EditExpenseRoute(transactionId))
                },
                onGoToAnalysisScreen = {
                    navController.navigate(AnalysisExpenseRoute)
                }
            )
        }
        composable<AddExpenseRoute> {
            AddExpenseScreen(
                appBarState = appBarState,
                onGoBackToExpensesScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<EditExpenseRoute> { entry ->
            val route: EditExpenseRoute = entry.toRoute()
            EditExpenseScreen(
                appBarState = appBarState,
                transactionId = route.expenseId,
                onGoBackToExpensesScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<AnalysisExpenseRoute> {
            AnalysisExpenseScreen(
                appBarState = appBarState,
                onGoBackToHistoryScreen = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}