package francisco.simon.myfinance.ui.features.account.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.NetworkError
import francisco.simon.myfinance.core.domain.utils.onError
import francisco.simon.myfinance.core.domain.utils.onSuccess
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.ui.features.account.mapper.toAccountUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for accounts, works with state
 * @param getAccountUseCase
 * @author Simon Francisco
 */
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)

    val state: StateFlow<AccountScreenState> = _state

    init {
        loadAccount()
    }

    private fun loadAccount() {
        updateLoading()
        viewModelScope.launch {
            getAccountUseCase().onSuccess { account ->
                updateSuccess(account)
            }.onError { error ->
                updateError(error)
            }
        }

    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            AccountScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateSuccess(account: Account) {
        _state.update {
            AccountScreenState.Success(account.toAccountUI())
        }
    }

    private fun updateLoading() {
        _state.update {
            AccountScreenState.Loading
        }
    }

    fun retry() {
        loadAccount()
    }
}