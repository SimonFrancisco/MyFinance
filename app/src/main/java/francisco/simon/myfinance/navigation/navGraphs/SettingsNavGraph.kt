package francisco.simon.myfinance.navigation.navGraphs

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.SettingsGraph
import francisco.simon.myfinance.navigation.SettingsGraph.SettingsRoute
import francisco.simon.myfinance.ui.features.settings.screens.settings.SettingsScreen

/**
 * Extension function for Settings nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.settingsNavGraph(appBarState: MutableState<AppBarState>) {
    navigation<SettingsGraph>(startDestination = SettingsRoute) {
        composable<SettingsRoute> {
            SettingsScreen(appBarState)
        }
    }
}