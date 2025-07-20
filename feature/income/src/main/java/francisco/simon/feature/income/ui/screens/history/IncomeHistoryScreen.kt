package francisco.simon.feature.income.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.history.HistoryScreen
import francisco.simon.feature.income.incomeComponent

@Composable
internal fun IncomeHistoryScreen(
    appBarState: MutableState<AppBarState>,
    onGoBackToIncomeScreen: () -> Unit,
    onGoToEditIncomeScreen: (Int) -> Unit,
    onGoToAnalysisScreen: () -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.history_app_top_bar,
        navigationButton = NavigationButton.Back {
            onGoBackToIncomeScreen()
        },
        actionButton = ActionButton(R.drawable.ic_analysis) {
            onGoToAnalysisScreen()
        }
    )
    val component = incomeComponent()
    val viewModel: IncomeHistoryScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    HistoryScreen(viewModel = viewModel, onGoToEditIncomeScreen)
}