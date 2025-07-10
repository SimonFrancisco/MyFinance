package francisco.simon.myfinance.core.ui.navigationBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import francisco.simon.core.ui.theme.Green
import francisco.simon.myfinance.navigation.routeClass

/**
 * Navigation bar - main function
 * @author Simon Francisco
 */
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
            NavigationItem(currentTab, tab, navController)
        }
    }
}

/**
 * Settings for each nav item
 * @author Simon Francisco
 */
@Composable
private fun RowScope.NavigationItem(
    currentTab: AppTab?,
    tab: AppTab,
    navController: NavController
) {
    NavigationBarItem(
        selected = currentTab == tab,
        onClick = {
            if (currentTab != null) {
                navController.navigate(tab.graph) {
                    popUpTo(currentTab.graph) {
                        saveState = true
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        icon = {
            NavigationBarIcon(tab)
        },
        label = {
            NavigationBarLabel(tab)
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Green,
            selectedTextColor = Color.Black
        )
    )
}

@Composable
private fun NavigationBarLabel(tab: AppTab) {
    Text(
        text = stringResource(tab.labelRes),
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun NavigationBarIcon(tab: AppTab) {
    Icon(
        painter = painterResource(tab.iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(16.dp)
    )
}