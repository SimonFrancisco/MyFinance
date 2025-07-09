package francisco.simon.myfinance.ui.features.account.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.NetworkError
import francisco.simon.myfinance.core.domain.utils.onError
import francisco.simon.myfinance.core.domain.utils.onSuccess
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.model.AccountUpdateRequestModel
import francisco.simon.myfinance.domain.usecase.GetAccountByIdUseCase
import francisco.simon.myfinance.domain.usecase.UpdateAccountUseCase
import francisco.simon.myfinance.ui.features.account.mapper.toAccountUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountEditViewModel @Inject constructor(
    private val accountId: Int,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<AccountEditScreenState> =
        MutableStateFlow(AccountEditScreenState.Loading)

    val state: StateFlow<AccountEditScreenState> = _state

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    private lateinit var latestAccountUpdateRequestModel: AccountUpdateRequestModel

    init {
        loadAccount()
    }

    private fun loadAccount() {
        updateLoading()
        viewModelScope.launch {
            getAccountByIdUseCase(accountId).onSuccess { account ->
                updateSuccess(account)
                latestAccountUpdateRequestModel = AccountUpdateRequestModel(
                    id = accountId,
                    name = account.name,
                    balance = account.balance,
                    currency = account.currency
                )
            }.onError { error ->
                updateError(error)
            }
        }
    }

    fun updateAccount(
        name: String,
        currency: String,
        balance: String
    ) {
        updateLoading()
        viewModelScope.launch {
            latestAccountUpdateRequestModel = AccountUpdateRequestModel(
                id = accountId,
                name = name,
                currency = currency,
                balance = balance
            )
            updateAccountUseCase(latestAccountUpdateRequestModel).onSuccess { _ ->
                goBack()
            }.onError { error ->
                updateError(error)
            }
        }
    }

    private suspend fun goBack() {
        _exitChannel.send(Unit)
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            AccountEditScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateSuccess(account: Account) {
        _state.update {
            AccountEditScreenState.Success(account.toAccountUI())
        }
    }

    private fun updateLoading() {
        _state.update {
            AccountEditScreenState.Loading
        }
    }

    fun retry() {
        if (::latestAccountUpdateRequestModel.isInitialized) {
            updateAccount(
                name = latestAccountUpdateRequestModel.name,
                currency = latestAccountUpdateRequestModel.currency,
                balance = latestAccountUpdateRequestModel.balance
            )
        } else {
            loadAccount()
        }
    }

    fun updateLatestAccountRequestModel(accountUpdateRequestModel: AccountUpdateRequestModel) {
        latestAccountUpdateRequestModel = accountUpdateRequestModel
    }


    /*@AssistedFactory
    interface Factory {
        fun create(accountId: Int): AccountEditViewModel
    }*/
}