package francisco.simon.myfinance.ui.features.expense.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.NavigationButton
import francisco.simon.myfinance.core.ui.history.HistoryScreen
import francisco.simon.myfinance.navigation.LocalNavController

@Composable
fun ExpensesHistoryScreen(appBarConfig: (AppBarState) -> Unit) {
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.history_app_top_bar,
                navigationButton = NavigationButton.Back {
                    navController.popBackStack()
                },
                actionButton = ActionButton(R.drawable.ic_analysis) {}
            )
        )
    }
    val viewModel: ExpensesHistoryScreenViewModel = hiltViewModel()
    HistoryScreen(viewModel = viewModel)
}