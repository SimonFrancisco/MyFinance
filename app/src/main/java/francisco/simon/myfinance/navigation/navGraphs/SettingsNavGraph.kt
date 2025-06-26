package francisco.simon.myfinance.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.SettingsGraph
import francisco.simon.myfinance.navigation.SettingsGraph.SettingsRoute
import francisco.simon.myfinance.ui.features.settings.screens.settings.SettingsScreen

fun NavGraphBuilder.settingsNavGraph(appBarState: AppBarState) {
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