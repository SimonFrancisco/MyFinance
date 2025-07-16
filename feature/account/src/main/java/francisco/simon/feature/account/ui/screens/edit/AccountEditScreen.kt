package francisco.simon.feature.account.ui.screens.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.utils.EventConsumer
import francisco.simon.core.ui.utils.MonitorAccount
import francisco.simon.core.ui.utils.MonitorAccount.Commands.UPDATE_ACCOUNT
import francisco.simon.feature.account.accountComponent
import francisco.simon.feature.account.ui.screens.edit.AccountEditViewModel.UpdateModel
import francisco.simon.feature.account.ui.screens.edit.component.BottomSheet

@Composable
internal fun AccountEditScreen(
    accountId: Int,
    appBarState: MutableState<AppBarState>,
    onGoBackToAccountScreen: () -> Unit
) {
    val context = LocalContext.current
    val component = accountComponent()
        .getAccountEditComponentFactory()
        .create(accountId)

    val viewModel: AccountEditViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value

    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.account_app_top_bar,
        navigationButton = NavigationButton.Close {
            onGoBackToAccountScreen()
        },
        actionButton = ActionButton(
            icon = R.drawable.ic_confirm
        ) {
            viewModel.updateAccount()
            MonitorAccount.event(UPDATE_ACCOUNT)

        }
    )
    EventConsumer(channel = viewModel.exitChannel) {
        onGoBackToAccountScreen()
    }
    EventConsumer(viewModel.dataMissingError) {
        Toast.makeText(
            context,
            context.getString(R.string.data_not_complete_error), Toast.LENGTH_SHORT
        ).show()
    }
    AccountEditScreenContent(
        currentState = currentState,
        updateModelState = viewModel.updateModel,
        viewModel = viewModel,
    )
}

@Composable
private fun AccountEditScreenContent(
    currentState: AccountEditScreenState,
    updateModelState: MutableState<UpdateModel>,
    viewModel: AccountEditViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val showSheet = remember { mutableStateOf(false) }
        AccountEditScreenName(updateModelState)
        HorizontalDivider()
        AccountEditScreenBalance(updateModelState)
        HorizontalDivider()
        AccountEdiScreenCurrency(updateModelState) {
            showSheet.value = true
        }
        BottomSheet(
            showSheet = showSheet,
            updateModelState = updateModelState
        )
        when (currentState) {
            is AccountEditScreenState.Error -> {
                francisco.simon.core.ui.components.RetryCall(
                    errorRes = currentState.errorMessageRes
                ) {
                    viewModel.retry()
                }
            }
            is AccountEditScreenState.Loading -> {
                FullScreenLoading()
            }
            is AccountEditScreenState.Success -> Unit
        }
    }
}
