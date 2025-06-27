package francisco.simon.myfinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.myfinance.core.components.topBar.LocalAppBarState
import francisco.simon.myfinance.navigation.navGraphs.accountNavGraph
import francisco.simon.myfinance.navigation.navGraphs.categoryNavGraph
import francisco.simon.myfinance.navigation.navGraphs.expenseNavGraph
import francisco.simon.myfinance.navigation.navGraphs.incomeNavGraph
import francisco.simon.myfinance.navigation.navGraphs.settingsNavGraph
import francisco.simon.myfinance.ui.features.splash.SplashScreen

/**
 * App nav graph for the whole app, it contains all needed graphs
 * @author Simon Francisco
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier,

) {
    val appBarState = LocalAppBarState.current
    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            composable<SplashRoute> {
                SplashScreen()
            }
            expenseNavGraph(appBarState)
            incomeNavGraph(appBarState)
            accountNavGraph(appBarState)
            categoryNavGraph(appBarState)
            settingsNavGraph(appBarState)
        }
    }
}










