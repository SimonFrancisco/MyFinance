package francisco.simon.feature.income.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.income.navigation.IncomeGraph.IncomeHistoryRoute
import francisco.simon.feature.income.navigation.IncomeGraph.IncomeRoute
import francisco.simon.feature.income.ui.screens.history.IncomeHistoryScreen
import francisco.simon.feature.income.ui.screens.income.IncomeScreen

/**
 * Extension function for Income nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.incomeNavGraph(
    appBarState: MutableState<AppBarState>,
    navController: NavController
) {
    navigation<IncomeGraph>(startDestination = IncomeRoute) {
        composable<IncomeRoute> {
            IncomeScreen(
                appBarState = appBarState,
                onGoToIncomeHistoryScreen = {
                    navController.navigate(IncomeHistoryRoute)
                }
            )
        }
        composable<IncomeHistoryRoute> {
            IncomeHistoryScreen(
                appBarState = appBarState,
                onGoBackToIncomeScreen = {
                    navController.safePopBackStack()
                })
        }
    }
}