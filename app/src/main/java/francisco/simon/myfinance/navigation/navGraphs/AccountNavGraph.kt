package francisco.simon.myfinance.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.navigation.AccountGraph
import francisco.simon.myfinance.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.ui.features.account.screens.AccountScreen

/**
 * Extension function for Account nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.accountNavGraph() {
    navigation<AccountGraph>(startDestination = AccountRoute) {
        composable<AccountRoute> {
            AccountScreen()
        }
    }
}