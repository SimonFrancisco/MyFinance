package francisco.simon.feature.expenses.ui.screens.add_expense

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.transactions.addTransaction.AddTransactionScreen
import francisco.simon.core.ui.utils.EventConsumer
import francisco.simon.feature.expenses.expensesComponent

@Composable
fun AddExpenseScreen(appBarState: MutableState<AppBarState>, onGoBackToExpensesScreen: () -> Unit) {
    val component = expensesComponent()
    val viewModel: AddExpenseScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )

    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.add_expense_app_top_bar,
        navigationButton = NavigationButton.Close {
            onGoBackToExpensesScreen()
        },
        actionButton = ActionButton(
            icon = R.drawable.ic_confirm
        ) {
            viewModel.onAddTransaction()
        }
    )
    val context = LocalContext.current
    AddTransactionScreen(viewModel)
    EventConsumer(viewModel.exitChannel) {
        onGoBackToExpensesScreen()
    }
    EventConsumer(viewModel.dataMissingError) {
        Toast.makeText(
            context,
            context.getString(R.string.data_not_complete_error), Toast.LENGTH_SHORT
        ).show()
    }

}