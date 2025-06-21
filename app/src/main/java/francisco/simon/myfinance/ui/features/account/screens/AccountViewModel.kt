package francisco.simon.myfinance.ui.features.account.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.utils.NetworkResult
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
        viewModelScope.launch {
            when (val account = getAccountUseCase()) {
                is NetworkResult.Error -> {
                    _state.update {
                        AccountScreenState.Error(account.message)
                    }
                }
                is NetworkResult.Exception -> {
                    _state.update {
                        AccountScreenState.Error(account.e.message.toString())
                    }
                }
                is NetworkResult.Success<Account> -> {
                    _state.update {
                        AccountScreenState.Success(account.data.toAccountUI())
                    }

                }
            }


        }

    }
    fun retry(){
        loadAccount()
    }
}