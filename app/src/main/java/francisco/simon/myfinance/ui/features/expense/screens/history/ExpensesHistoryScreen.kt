package francisco.simon.myfinance.ui.features.expense.screens.history

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.core.ui.history.HistoryScreen

@Composable
fun ExpensesHistoryScreen() {
    val viewModel: ExpensesHistoryScreenViewModel = hiltViewModel()
    HistoryScreen(viewModel = viewModel)
}