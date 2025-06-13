package francisco.simon.myfinance.ui.features.account.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.ui.features.account.mapper.toAccountUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)

    val state: StateFlow<AccountScreenState> = _state

    init {
        loadAccount()
    }

    private val fakeAccount = Account(
        id = 1,
        name = "Simon",
        balance = "500 000",
        currency = "RUB"
    ).toAccountUI()

    private fun loadAccount() {
        viewModelScope.launch {
            delay(SIMULATE_LOADING_DELAY)
            _state.update {
                AccountScreenState.Success(fakeAccount)
            }
        }
    }

    companion object {
        private const val SIMULATE_LOADING_DELAY = 2000L
    }
}