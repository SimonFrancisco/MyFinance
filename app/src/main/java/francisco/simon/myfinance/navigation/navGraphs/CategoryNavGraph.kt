package francisco.simon.myfinance.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.CategoryGraph
import francisco.simon.myfinance.navigation.CategoryGraph.CategoryRoute
import francisco.simon.myfinance.ui.features.category.screens.CategoryScreen

/**
 * Extension function for Category nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.categoryNavGraph(appBarState: AppBarState) {
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
}