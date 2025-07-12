package francisco.simon.feature.expenses.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.history.HistoryScreen
import francisco.simon.feature.expenses.expensesComponent

@Composable
internal fun ExpensesHistoryScreen(appBarState: MutableState<AppBarState>, onGoBackToExpensesScreen:()->Unit) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.history_app_top_bar,
        navigationButton = NavigationButton.Back {
            onGoBackToExpensesScreen()
        },
        actionButton = ActionButton(R.drawable.ic_analysis) {}
    )
    val component = expensesComponent()
    val viewModel: ExpensesHistoryScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    HistoryScreen(viewModel = viewModel)
}