package francisco.simon.myfinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.feature.account.navigation.accountNavGraph
import francisco.simon.feature.category.navigation.categoryNavGraph
import francisco.simon.feature.expenses.navigation.ExpenseGraph
import francisco.simon.feature.expenses.navigation.expenseNavGraph
import francisco.simon.feature.income.navigation.incomeNavGraph
import francisco.simon.feature.settings.navigation.settingsNavGraph
import francisco.simon.myfinance.SplashScreen

/**
 * App nav graph for the whole app, it contains all needed graphs
 * @author Simon Francisco
 */
@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any,
    appBarState: MutableState<AppBarState>,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onGoToScreenAfterSplash = {
                    navController.navigate(ExpenseGraph) {
                        popUpTo(SplashRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        expenseNavGraph(
            appBarState = appBarState,
            navController = navController
        )
        incomeNavGraph(
            appBarState = appBarState,
            navController = navController
        )
        accountNavGraph(
            appBarState = appBarState,
            navController = navController
        )
        categoryNavGraph(
            appBarState = appBarState
        )
        settingsNavGraph(
            appBarState = appBarState,
            navController = navController
        )
    }

}










