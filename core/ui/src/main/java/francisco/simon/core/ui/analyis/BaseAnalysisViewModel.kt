package francisco.simon.core.ui.analyis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.model.CategoryStatsModel
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

abstract class BaseAnalysisViewModel : ViewModel() {

    private val _state =
        MutableStateFlow<AnalysisScreenState>(AnalysisScreenState.Loading)
    val state: StateFlow<AnalysisScreenState> = _state

    protected abstract suspend fun getAccount(): Result<Account, Error>

    protected abstract suspend fun getTransactions(
        account: Account,
        startDate: String,
        endDate: String
    ): Flow<Result<List<CategoryStatsModel>, Error>>

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
                        if (transactions.isEmpty()) {
                            _state.update {
                                AnalysisScreenState.Empty
                            }
                        } else {
                            _state.update {
                                AnalysisScreenState.Success(transactions)
                            }
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
            AnalysisScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateLoading() {
        _state.update {
            AnalysisScreenState.Loading
        }
    }
}