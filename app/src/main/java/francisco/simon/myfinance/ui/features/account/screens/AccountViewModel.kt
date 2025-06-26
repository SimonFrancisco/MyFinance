package francisco.simon.myfinance.ui.features.account.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.core.domain.utils.NetworkError
import francisco.simon.myfinance.core.domain.utils.onError
import francisco.simon.myfinance.core.domain.utils.onSuccess
import francisco.simon.myfinance.ui.features.account.mapper.toAccountUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
        _state.update {
            AccountScreenState.Loading
        }
        viewModelScope.launch {
            getAccountUseCase().onSuccess { account ->
                _state.update {
                    AccountScreenState.Success(account.toAccountUI())
                }

            }.onError { error ->
                val errorRes = (error as NetworkError).toStringRes()
                _state.update {
                    AccountScreenState.Error(errorMessageRes = errorRes)
                }
            }

        }

    }

    fun retry() {
        loadAccount()
    }
}