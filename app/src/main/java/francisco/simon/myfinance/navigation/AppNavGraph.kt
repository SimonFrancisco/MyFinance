package francisco.simon.myfinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.feature.account.navigation.accountNavGraph
import francisco.simon.feature.category.navigation.categoryNavGraph
import francisco.simon.feature.income.navigation.incomeNavGraph
import francisco.simon.myfinance.navigation.navGraphs.expenseNavGraph
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
    appBarState: MutableState<AppBarState>,
    modifier: Modifier = Modifier,
) {
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
            incomeNavGraph(
                appBarState = appBarState,
                navController = navController
            )
            accountNavGraph(
                appBarState = appBarState,
                navController = navController
            )
            categoryNavGraph(appBarState)
            settingsNavGraph(appBarState)
        }
    }
}










