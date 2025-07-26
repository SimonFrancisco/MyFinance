package francisco.simon.feature.settings.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.settings.navigation.SettingsGraph.AboutRoute
import francisco.simon.feature.settings.navigation.SettingsGraph.LanguageRoute
import francisco.simon.feature.settings.navigation.SettingsGraph.PrimaryColorRoute
import francisco.simon.feature.settings.navigation.SettingsGraph.SettingsRoute
import francisco.simon.feature.settings.navigation.SettingsGraph.SyncRoute
import francisco.simon.feature.settings.ui.screens.about.AboutScreen
import francisco.simon.feature.settings.ui.screens.language.LanguageScreen
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
    navigation<SettingsGraph>(startDestination = SettingsRoute) {
        composable<SettingsRoute> {
            SettingsScreen(
                appBarState = appBarState,
                onGoToSync = {
                    navController.navigate(SyncRoute)
                },
                onGoToPrimaryColor = {
                    navController.navigate(PrimaryColorRoute)
                },
                onGoToAbout = {
                    navController.navigate(AboutRoute)
                },
                onGoToLanguage = {
                    navController.navigate(LanguageRoute)
                }
            )
        }
        composable<SyncRoute> {
            SyncScreen(
                appBarState = appBarState,
                onGoBackToSettingScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<PrimaryColorRoute> {
            PrimaryColorScreen(
                appBarState = appBarState,
                onGoBackToSettingScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<AboutRoute> {
            AboutScreen(
                appBarState = appBarState,
                onGoBackToSettingScreen = {
                    navController.safePopBackStack()
                }
            )
        }
        composable<LanguageRoute> {
            LanguageScreen(
                appBarState = appBarState,
                onGoBackToSettingScreen = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}