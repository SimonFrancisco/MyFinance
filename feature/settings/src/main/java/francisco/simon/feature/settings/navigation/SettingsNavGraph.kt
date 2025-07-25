package francisco.simon.feature.settings.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.settings.ui.screens.primary_color.PrimaryColorScreen
import francisco.simon.feature.settings.ui.screens.settings.SettingsScreen
import francisco.simon.feature.settings.ui.screens.sync.SyncScreen

/**
 * Extension function for Settings nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.settingsNavGraph(
    appBarState: MutableState<AppBarState>,
    navController: NavController
) {
    navigation<SettingsGraph>(startDestination = SettingsGraph.SettingsRoute) {
        composable<SettingsGraph.SettingsRoute> {
            SettingsScreen(
                appBarState = appBarState,
                onGoToSync = {
                    navController.navigate(SettingsGraph.SyncRoute)
                },
                onGoToPrimaryColor = {
                    navController.navigate(SettingsGraph.PrimaryColorRoute)
                })
        }
        composable<SettingsGraph.SyncRoute> {
            SyncScreen(
                appBarState = appBarState,
                onGoBackToSettingScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<SettingsGraph.PrimaryColorRoute> {
            PrimaryColorScreen(
                appBarState = appBarState,
                onGoBackToSettingScreen = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}