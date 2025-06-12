package francisco.simon.myfinance.ui.features.category.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.topBar.AppBarState

@Composable
fun CategoryScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.category_app_top_bar
            )
        )
    }
}
