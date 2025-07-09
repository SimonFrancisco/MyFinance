package francisco.simon.myfinance.ui.features.expense.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.NavigationButton
import francisco.simon.myfinance.core.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.myfinance.core.ui.history.HistoryScreen
import francisco.simon.myfinance.core.ui.utils.safePopBackStack
import francisco.simon.myfinance.getApplicationComponent
import francisco.simon.myfinance.navigation.LocalNavController

@Composable
fun ExpensesHistoryScreen(appBarState: MutableState<AppBarState>) {
    val navController = LocalNavController.current
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.history_app_top_bar,
        navigationButton = NavigationButton.Back {
            navController.safePopBackStack()
        },
        actionButton = ActionButton(R.drawable.ic_analysis) {}
    )
    val component = getApplicationComponent()
    val viewModel: ExpensesHistoryScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    HistoryScreen(viewModel = viewModel)
}