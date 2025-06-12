package francisco.simon.myfinance.ui.features.expense.screens.expense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState

@Composable
fun ExpenseScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.expense_app_top_bar,
                actionButton = ActionButton(R.drawable.ic_history) {
                }
            )
        )
    }
}