package francisco.simon.feature.account.ui.screens.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.onError
import francisco.simon.core.domain.utils.onSuccess
import francisco.simon.core.ui.utils.toStringRes
import francisco.simon.feature.account.di.AccountIdQualifier
import francisco.simon.feature.account.domain.GetAccountByIdUseCase
import francisco.simon.feature.account.domain.UpdateAccountUseCase
import francisco.simon.feature.account.ui.mapper.toAccountUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class AccountEditViewModel @Inject constructor(
    @AccountIdQualifier private val accountId: Int,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<AccountEditScreenState> =
        MutableStateFlow(AccountEditScreenState.Loading)

    val state: StateFlow<AccountEditScreenState> = _state

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    private val _dataMissingError = Channel<Unit>()
    val dataMissingError: ReceiveChannel<Unit> = _dataMissingError

    val updateModel: MutableState<UpdateModel> = mutableStateOf(UpdateModel())

    init {
        loadAccount()
    }

    private fun loadAccount() {
        updateLoading()
        viewModelScope.launch {
            getAccountByIdUseCase(accountId).onSuccess { account ->
                updateModel.value = updateModel.value.copy(id = accountId)
                updateModel.value = updateModel.value.copy(name = account.name)
                updateModel.value = updateModel.value.copy(balance = account.balance)
                updateModel.value = updateModel.value.copy(currency = account.currency)
                updateSuccess(account)
            }.onError { error ->
                updateError(error)
            }
        }
    }

    fun updateAccount() {
        viewModelScope.launch {
            if (!validateUpdateModel()) {
                _dataMissingError.send(Unit)
                return@launch
            }
            updateLoading()
            val accountUpdateRequestModel = AccountUpdateRequestModel(
                id = accountId,
                name = updateModel.value.name?.trim() ?: "",
                currency = updateModel.value.currency ?: "",
                balance = updateModel.value.balance?.trim() ?: ""
            )
            updateAccountUseCase(accountUpdateRequestModel).onSuccess { _ ->
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
        if (updateModel.value.id != null) {
            updateAccount()
        } else {
            loadAccount()
        }

    }

    private fun validateUpdateModel(): Boolean {
        return with(updateModel.value) {
            !name?.trim().isNullOrEmpty()
                    && !balance?.trim().isNullOrEmpty()
                    && balance?.trim()?.contains(',') == false
                    && balance.trim().count { it == '.' } == 1
        }
    }

    internal data class UpdateModel(
        val id: Int? = null,
        val name: String? = null,
        val balance: String? = null,
        val currency: String? = null
    )

}