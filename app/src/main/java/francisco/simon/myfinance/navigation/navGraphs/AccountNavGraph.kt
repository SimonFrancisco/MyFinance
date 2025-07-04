package francisco.simon.myfinance.navigation.navGraphs

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.AccountGraph
import francisco.simon.myfinance.navigation.AccountGraph.AccountEditRoute
import francisco.simon.myfinance.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.ui.features.account.screens.account.AccountScreen
import francisco.simon.myfinance.ui.features.account.screens.edit.AccountEditScreen

/**
 * Extension function for Account nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.accountNavGraph(appBarState: MutableState<AppBarState>) {
    navigation<AccountGraph>(startDestination = AccountRoute) {
        composable<AccountRoute> {
            AccountScreen(appBarState)
        }
        composable<AccountEditRoute> { entry ->
            val route: AccountEditRoute = entry.toRoute()
            AccountEditScreen(
                accountId = route.accountId,
                appBarState = appBarState
            )
        }
    }
}