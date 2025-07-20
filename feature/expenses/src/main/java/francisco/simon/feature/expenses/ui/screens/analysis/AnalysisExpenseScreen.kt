package francisco.simon.feature.expenses.ui.screens.analysis

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.analyis.AnalysisScreen
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.feature.expenses.expensesComponent

@Composable
fun AnalysisExpenseScreen(
    appBarState: MutableState<AppBarState>,
    onGoBackToHistoryScreen: () -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.analysis_app_top_bar,
        navigationButton = NavigationButton.Close {
            onGoBackToHistoryScreen()
        }
    )
    val component = expensesComponent()
    val viewModel: AnalysisExpensesScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    AnalysisScreen(viewModel = viewModel)
}