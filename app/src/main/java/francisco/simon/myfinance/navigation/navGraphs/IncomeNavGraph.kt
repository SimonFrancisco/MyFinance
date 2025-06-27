package francisco.simon.myfinance.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.IncomeGraph
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeHistoryRoute
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeRoute
import francisco.simon.myfinance.ui.features.income.screens.history.IncomeHistoryScreen
import francisco.simon.myfinance.ui.features.income.screens.income.IncomeScreen

fun NavGraphBuilder.incomeNavGraph(appBarState: AppBarState) {
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
        composable<IncomeHistoryRoute> {
            IncomeHistoryScreen {
                with(appBarState) {
                    titleRes = it.titleRes
                    navigationButton = it.navigationButton
                    actionButton = it.actionButton
                }
            }
        }
    }
}