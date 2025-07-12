package francisco.simon.feature.category.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.feature.category.ui.screens.CategoryScreen

/**
 * Extension function for Category nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.categoryNavGraph(appBarState: MutableState<AppBarState>) {
    navigation<CategoryRouteGraph>(startDestination = CategoryRouteGraph.CategoryRoute) {
        composable<CategoryRouteGraph.CategoryRoute> {
            CategoryScreen(appBarState)
        }
    }
}