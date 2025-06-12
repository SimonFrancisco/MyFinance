package francisco.simon.myfinance.ui.features.settings.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.topBar.AppBarState

@Composable
fun SettingsScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.settings_app_top_bar,
            )
        )
    }
}