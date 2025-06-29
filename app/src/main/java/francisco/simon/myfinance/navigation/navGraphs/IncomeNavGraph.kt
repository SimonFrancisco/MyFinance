package francisco.simon.myfinance.navigation.navGraphs

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.IncomeGraph
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeHistoryRoute
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeRoute
import francisco.simon.myfinance.ui.features.income.screens.history.IncomeHistoryScreen
import francisco.simon.myfinance.ui.features.income.screens.income.IncomeScreen

/**
 * Extension function for Income nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.incomeNavGraph(appBarState: MutableState<AppBarState>) {
    navigation<IncomeGraph>(startDestination = IncomeRoute) {
        composable<IncomeRoute> {
            IncomeScreen(appBarState)
        }
        composable<IncomeHistoryRoute> {
            IncomeHistoryScreen(appBarState)
        }
    }
}