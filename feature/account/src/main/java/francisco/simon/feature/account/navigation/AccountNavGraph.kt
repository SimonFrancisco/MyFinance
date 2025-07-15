package francisco.simon.feature.account.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.utils.safePopBackStack
import francisco.simon.feature.account.navigation.AccountGraph.AccountEditRoute
import francisco.simon.feature.account.navigation.AccountGraph.AccountRoute
import francisco.simon.feature.account.ui.screens.account.AccountScreen
import francisco.simon.feature.account.ui.screens.edit.AccountEditScreen

/**
 * Extension function for Account nav graph, it contains all needed routes
 * @author Simon Francisco
 */
fun NavGraphBuilder.accountNavGraph(
    appBarState: MutableState<AppBarState>,
    navController: NavController
) {

    navigation<AccountGraph>(startDestination = AccountRoute) {
        composable<AccountRoute> {
            AccountScreen(
                appBarState = appBarState,
                onOpenEditScreen = { accountId ->
                    navController.navigate(AccountEditRoute(accountId))
                }
            )
        }
        composable<AccountEditRoute> { entry ->
            val route: AccountEditRoute = entry.toRoute()
            AccountEditScreen(
                accountId = route.accountId,
                appBarState = appBarState, onGoBackToAccountScreen = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}