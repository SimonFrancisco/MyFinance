package francisco.simon.feature.settings.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.feature.settings.ui.SettingsScreen

/**
 * Extension function for Settings nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.settingsNavGraph(appBarState: MutableState<AppBarState>) {
    navigation<SettingsGraph>(startDestination = SettingsGraph.SettingsRoute) {
        composable<SettingsGraph.SettingsRoute> {
            SettingsScreen(appBarState)
        }
    }
}