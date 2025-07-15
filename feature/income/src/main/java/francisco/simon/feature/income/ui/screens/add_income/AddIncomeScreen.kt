package francisco.simon.feature.income.ui.screens.add_income

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import francisco.simon.core.ui.utils.MonitorAccount
import francisco.simon.core.ui.utils.MonitorTransaction
import francisco.simon.feature.income.incomeComponent

@Composable
fun AddIncomeScreen(appBarState: MutableState<AppBarState>, onGoBackToIncomeScreen: () -> Unit) {
    val component = incomeComponent()
    val viewModel: AddIncomeScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )

    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.add_income_app_top_bar,
        navigationButton = NavigationButton.Close {
            onGoBackToIncomeScreen()
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
        onGoBackToIncomeScreen()
        MonitorTransaction.event(MonitorTransaction.Commands.MANIPULATE_INCOME)
    }
    EventConsumer(viewModel.dataMissingError) {
        Toast.makeText(
            context,
            context.getString(R.string.data_not_complete_error), Toast.LENGTH_SHORT
        ).show()
    }

}