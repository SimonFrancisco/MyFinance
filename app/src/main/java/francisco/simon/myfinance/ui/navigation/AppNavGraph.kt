package francisco.simon.myfinance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.ui.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.ui.navigation.CategoryGraph.CategoryRoute
import francisco.simon.myfinance.ui.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.myfinance.ui.navigation.IncomeGraph.IncomeRoute
import francisco.simon.myfinance.ui.navigation.SettingsGraph.SettingsRoute

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: ExpenseGraph) {
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            navigation<ExpenseGraph>(startDestination = ExpenseRoute) {
                composable<ExpenseRoute> {

                }
            }
            navigation<IncomeGraph>(startDestination = IncomeRoute) {
                composable<IncomeRoute> {

                }
            }
            navigation<AccountGraph>(startDestination = AccountRoute) {
                composable<AccountRoute> {

                }
            }
            navigation<CategoryGraph>(startDestination = CategoryRoute) {
                composable<CategoryRoute> {

                }
            }
            navigation<SettingsGraph>(startDestination = SettingsRoute) {
                composable<SettingsRoute> {

                }
            }
        }
    }
}