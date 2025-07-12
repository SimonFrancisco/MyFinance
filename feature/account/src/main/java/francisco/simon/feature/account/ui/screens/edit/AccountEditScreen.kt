package francisco.simon.feature.account.ui.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.utils.MonitorAccount
import francisco.simon.core.ui.utils.MonitorAccount.Commands.UPDATE_ACCOUNT
import francisco.simon.feature.account.accountComponent
import francisco.simon.feature.account.ui.screens.edit.component.BottomSheet

// TODO double tab on Account tab resets data, probably because of the way I am saving it.
@Composable
internal fun AccountEditScreen(
    accountId: Int,
    appBarState: MutableState<AppBarState>,
    onGoBackToAccountScreen: () -> Unit
) {
    val component = accountComponent()
        .getAccountEditComponentFactory()
        .create(accountId)

    val viewModel: AccountEditViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value

    //TODO come up with a way to store data when coming back from another screen
    /**
     * Current solution resets the values to what we had from
     * the very beginning - not very good solution in terms of UX
     */
    val updateModelState: MutableState<AccountUpdateRequestModel?> = remember {
        if (currentState is AccountEditScreenState.Success) {
            mutableStateOf(
                AccountUpdateRequestModel(
                    id = currentState.account.id,
                    name = currentState.account.name,
                    balance = currentState.account.balance.toString(),
                    currency = currentState.account.currency
                )
            )
        } else {
            mutableStateOf(null)
        }
    }
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.account_app_top_bar,
        navigationButton = NavigationButton.Close {
            onGoBackToAccountScreen()
        },
        actionButton = ActionButton(
            icon = R.drawable.ic_confirm
        ) {
            updateModelState.value?.let {
                viewModel.updateAccount(
                    name = it.name,
                    currency = it.currency,
                    balance = it.balance
                )
                MonitorAccount.event(UPDATE_ACCOUNT)
            }
        }
    )
    francisco.simon.core.ui.utils.EventConsumer(channel = viewModel.exitChannel) {
        onGoBackToAccountScreen()
    }
    AccountEditScreenContent(
        currentState = currentState,
        updateModelState = updateModelState,
        viewModel = viewModel,
    )
}

@Composable
private fun AccountEditScreenContent(
    currentState: AccountEditScreenState,
    updateModelState: MutableState<AccountUpdateRequestModel?>,
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
                    updateModelState.value?.let {
                        viewModel.updateLatestAccountRequestModel(it)
                    }
                    viewModel.retry()
                }
            }

            is AccountEditScreenState.Loading -> {
                francisco.simon.core.ui.components.FullScreenLoading()
            }

            is AccountEditScreenState.Success -> {
                with(currentState.account) {
                    val currentAccountModel = AccountUpdateRequestModel(
                        id = id,
                        name = name,
                        balance = balance.toString(),
                        currency = currency
                    )
                    updateModelState.value = currentAccountModel
                }
            }
        }
    }
}
