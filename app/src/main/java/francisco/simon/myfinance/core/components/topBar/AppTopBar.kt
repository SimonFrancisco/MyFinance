package francisco.simon.myfinance.core.components.topBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import francisco.simon.myfinance.R
import francisco.simon.myfinance.ui.theme.Green

/***
 * The navigation buttons supports two actions: Close and Back
 * @author Simon Francisco
 */

abstract class NavigationButton(
    @DrawableRes val icon: Int,
    val action: () -> Unit
) {
    class Back(action: () -> Unit) : NavigationButton(R.drawable.ic_back, action)

    class Close(action: () -> Unit) : NavigationButton(R.drawable.ic_close, action)
}

class ActionButton(@DrawableRes val icon: Int, val action: () -> Unit)


/***
 * General App bar state that is changed according to the screen being showed!
 * Check nav graphs
 * @author Simon Francisco
 */
class AppBarState(
    @StringRes titleRes: Int,
    navigationButton: NavigationButton? = null,
    actionButton: ActionButton? = null
) {
    var titleRes by mutableIntStateOf(titleRes)
    var navigationButton by mutableStateOf(navigationButton)
    var actionButton by mutableStateOf(actionButton)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    appBarState: AppBarState
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(appBarState.titleRes),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            NavigationIcon(appBarState)
        },
        actions = {
            ActionIcon(appBarState)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Green
        )

    )
}

@Composable
private fun ActionIcon(appBarState: AppBarState) {
    val actionButton = appBarState.actionButton
    if (actionButton != null) {
        IconButton(
            onClick = actionButton.action
        ) {
            Icon(
                painter = painterResource(actionButton.icon),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun NavigationIcon(appBarState: AppBarState) {
    val navigationButton = appBarState.navigationButton
    if (navigationButton != null) {
        IconButton(
            onClick = navigationButton.action

        ) {
            Icon(
                painter = painterResource(navigationButton.icon),
                contentDescription = null
            )
        }
    }
}