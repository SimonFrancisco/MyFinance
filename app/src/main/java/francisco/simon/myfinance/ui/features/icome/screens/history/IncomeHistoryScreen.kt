package francisco.simon.myfinance.ui.features.icome.screens.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.history.HistoryScreen
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.NavigationButton
import francisco.simon.myfinance.ui.navigation.LocalNavController

@Composable
fun IncomeHistoryScreen(appBarConfig: (AppBarState) -> Unit) {
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.history_app_top_bar,
                navigationButton = NavigationButton.Back {
                    navController.popBackStack()
                },
                actionButton = ActionButton(R.drawable.ic_analysis) {

                }
            )
        )
    }
    val viewModel: IncomeHistoryScreenViewModel = hiltViewModel()
    HistoryScreen(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
}