package francisco.simon.myfinance.ui.features.account.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState

@Composable
fun AccountScreen(appBarConfig: (AppBarState) -> Unit) {

    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.account_app_top_bar,
                actionButton = ActionButton(
                    icon = R.drawable.ic_edit
                ) {

                }
            )
        )
    }
}
