package francisco.simon.core.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.onError
import francisco.simon.core.domain.utils.onSuccess
import francisco.simon.core.ui.utils.toStringRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base view model for history screen. Making use of inheritance and polymorphism.
 * Transaction type (income/expense) automatically changes according to the screen calling it.
 * @author Simon Francisco
 */
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
        updateLoading()
        viewModelScope.launch {
            getAccount().onError { error ->
                updateError(error)
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
                        updateError(error)
                    }

                }
            }
        }
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            HistoryScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateLoading() {
        _state.update {
            HistoryScreenState.Loading
        }
    }

}