package francisco.simon.feature.income.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.income.navigation.IncomeGraph.AddIncomeRoute
import francisco.simon.feature.income.navigation.IncomeGraph.AnalysisIncomeRoute
import francisco.simon.feature.income.navigation.IncomeGraph.EditIncomeRoute
import francisco.simon.feature.income.navigation.IncomeGraph.IncomeHistoryRoute
import francisco.simon.feature.income.navigation.IncomeGraph.IncomeRoute
import francisco.simon.feature.income.ui.screens.add_income.AddIncomeScreen
import francisco.simon.feature.income.ui.screens.analysis.AnalysisIncomeScreen
import francisco.simon.feature.income.ui.screens.edit_income.EditIncomeScreen
import francisco.simon.feature.income.ui.screens.history.IncomeHistoryScreen
import francisco.simon.feature.income.ui.screens.income.IncomeScreen
import francisco.simon.feature.income.ui.screens.income.onGoToAddIncomeScreenGlobal

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
                },
                onGoToAddIncomeScreen = {
                    navController.navigate(AddIncomeRoute)
                    onGoToAddIncomeScreenGlobal = null
                },
                onGoToEditIncomeScreen = { transitionId ->
                    navController.navigate(EditIncomeRoute(transitionId))

                }
            )
        }
        composable<IncomeHistoryRoute> {
            IncomeHistoryScreen(
                appBarState = appBarState,
                onGoBackToIncomeScreen = {
                    navController.safePopBackStack()
                },
                onGoToEditIncomeScreen = { transitionId ->
                    navController.navigate(EditIncomeRoute(transitionId))
                },
                onGoToAnalysisScreen = {
                    navController.navigate(AnalysisIncomeRoute)
                }
            )
        }
        composable<AddIncomeRoute> {
            AddIncomeScreen(
                appBarState = appBarState,
                onGoBackToIncomeScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<EditIncomeRoute> { entry ->
            val route: EditIncomeRoute = entry.toRoute()
            EditIncomeScreen(
                transactionId = route.incomeId,
                appBarState = appBarState,
                onGoBackToIncomeScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<AnalysisIncomeRoute> {
            AnalysisIncomeScreen(
                appBarState = appBarState,
                onGoBackToHistoryScreen = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}