package francisco.simon.myfinance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.core.components.topBar.LocalAppBarState
import francisco.simon.myfinance.ui.features.account.screens.AccountScreen
import francisco.simon.myfinance.ui.features.category.screens.CategoryScreen
import francisco.simon.myfinance.ui.features.expense.screens.expense.ExpenseScreen
import francisco.simon.myfinance.ui.features.icome.screens.income.IncomeScreen
import francisco.simon.myfinance.ui.features.settings.screens.settings.SettingsScreen
import francisco.simon.myfinance.ui.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.ui.navigation.CategoryGraph.CategoryRoute
import francisco.simon.myfinance.ui.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.myfinance.ui.navigation.IncomeGraph.IncomeRoute
import francisco.simon.myfinance.ui.navigation.SettingsGraph.SettingsRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier

) {
    val appBarState = LocalAppBarState.current

    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            navigation<ExpenseGraph>(startDestination = ExpenseRoute) {
                composable<ExpenseRoute> {
                    ExpenseScreen {
                        with(appBarState) {
                            titleRes = it.titleRes
                            navigationButton = it.navigationButton
                            actionButton = it.actionButton
                        }

                    }
                }
            }
            navigation<IncomeGraph>(startDestination = IncomeRoute) {
                composable<IncomeRoute> {
                    IncomeScreen {
                        with(appBarState) {
                            titleRes = it.titleRes
                            navigationButton = it.navigationButton
                            actionButton = it.actionButton
                        }
                    }
                }
            }
            navigation<AccountGraph>(startDestination = AccountRoute) {
                composable<AccountRoute> {
                    AccountScreen {
                        with(appBarState) {
                            titleRes = it.titleRes
                            navigationButton = it.navigationButton
                            actionButton = it.actionButton
                        }
                    }
                }
            }
            navigation<CategoryGraph>(startDestination = CategoryRoute) {
                composable<CategoryRoute> {
                    CategoryScreen {
                        with(appBarState) {
                            titleRes = it.titleRes
                            navigationButton = it.navigationButton
                            actionButton = it.actionButton
                        }
                    }
                }
            }
            navigation<SettingsGraph>(startDestination = SettingsRoute) {
                composable<SettingsRoute> {
                    SettingsScreen {
                        with(appBarState) {
                            titleRes = it.titleRes
                            navigationButton = it.navigationButton
                            actionButton = it.actionButton
                        }
                    }
                }
            }
        }
    }
}