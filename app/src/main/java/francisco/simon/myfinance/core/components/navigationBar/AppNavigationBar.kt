package francisco.simon.myfinance.core.components.navigationBar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import francisco.simon.myfinance.ui.navigation.routeClass
import francisco.simon.myfinance.ui.theme.Green

@Composable
fun AppNavigationBar(
    navController: NavController,
    tabs: List<AppTab>
) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val closestNavGraphDestination = currentBackStackEntry?.destination?.hierarchy?.first {
            it is NavGraph
        }
        val closestNavGraphClass = closestNavGraphDestination.routeClass()
        val currentTab = tabs.firstOrNull {
            it.graph::class == closestNavGraphClass
        }
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentTab == tab,
                onClick = {
                    if (currentTab != null) {
                        navController.navigate(tab.graph) {
                            popUpTo(currentTab.graph) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(tab.iconRes),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(tab.labelRes),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green
                )
            )
        }
    }
}