package francisco.simon.core.ui.components.topBar.topBarUpdate

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton

@Composable
fun UpdateAppBarState(
    appBarState: MutableState<AppBarState>,
    @StringRes titleRes: Int,
    navigationButton: NavigationButton? = null,
    actionButton: ActionButton? = null
) {
    SideEffect {
        appBarState.value = AppBarState(
            titleRes = titleRes,
            navigationButton = navigationButton,
            actionButton = actionButton
        )
    }
}
