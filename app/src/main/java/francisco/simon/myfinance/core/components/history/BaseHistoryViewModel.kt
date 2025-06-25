package francisco.simon.myfinance.core.components.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.utils.Error
import francisco.simon.myfinance.domain.utils.NetworkError
import francisco.simon.myfinance.domain.utils.Result
import francisco.simon.myfinance.domain.utils.onError
import francisco.simon.myfinance.domain.utils.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseHistoryViewModel : ViewModel() {

    private val _state =
        MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)
    val state: StateFlow<HistoryScreenState> = _state

    protected abstract suspend fun getAccount(): Result<Account, Error>

    protected abstract suspend fun getTransactions(
        account: Account,
        startDate: String,
        endDate: String
    ): Flow<Result<List<Transaction>, Error>>

    fun loadTransactions(startDate: String, endDate: String) {
        _state.update {
            HistoryScreenState.Loading
        }
        viewModelScope.launch {
            getAccount().onError { error ->
                val errorRes = (error as NetworkError).toStringRes()
                _state.update {
                    HistoryScreenState.Error(errorMessageRes = errorRes)
                }
            }.onSuccess { account ->
                getTransactions(
                    account = account,
                    startDate = startDate,
                    endDate = endDate
                ).collectLatest { result ->
                    result.onSuccess { transactions ->
                        _state.update {
                            HistoryScreenState.Success(transactions)
                        }
                    }.onError { error ->
                        val errorRes = (error as NetworkError).toStringRes()
                        _state.update {
                            HistoryScreenState.Error(errorMessageRes = errorRes)
                        }
                    }

                }
            }
        }
    }

}