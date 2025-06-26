package francisco.simon.myfinance.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.navigation.AccountGraph
import francisco.simon.myfinance.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.ui.features.account.screens.AccountScreen

fun NavGraphBuilder.accountNavGraph(appBarState: AppBarState) {
    navigation<AccountGraph>(startDestination = AccountRoute) {
        composable<AccountRoute> {
            AccountScreen {
                with(appBarState) {
                    titleRes = it.titleRes
                    navigationButton = it.navigationButton
                    actionButton = it.actionButton
                }
            }
        }
    }
}