package francisco.simon.myfinance.ui.features.income.screens.history

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.core.ui.history.HistoryScreen

@Composable
fun IncomeHistoryScreen() {
    val viewModel: IncomeHistoryScreenViewModel = hiltViewModel()
    HistoryScreen(viewModel = viewModel)
}