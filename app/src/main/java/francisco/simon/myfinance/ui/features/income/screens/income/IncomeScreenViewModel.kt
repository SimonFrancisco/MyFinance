package francisco.simon.myfinance.ui.features.income.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.NetworkError
import francisco.simon.myfinance.core.domain.utils.onError
import francisco.simon.myfinance.core.domain.utils.onSuccess
import francisco.simon.myfinance.core.mapper.toApiDate
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.usecase.GetIncomeUseCase
import francisco.simon.myfinance.ui.features.income.mapper.toListIncome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

/**
 * ViewModel for income, works with state
 * @param getIncomeUseCase
 * @param getAccountUseCase
 * @author Simon Francisco
 */
class IncomeScreenViewModel @Inject constructor(
    private val getIncomeUseCase: GetIncomeUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<IncomeScreenState> =
        MutableStateFlow(IncomeScreenState.Loading)
    val state: StateFlow<IncomeScreenState> = _state

    init {
        loadIncome()
    }

    private fun loadIncome() {
        updateLoading()
        viewModelScope.launch {
            getAccountUseCase().onError { error ->
                updateError(error)
            }.onSuccess { account ->
                val transactionModel = TransactionModel(
                    accountId = account.id,
                    startDate = Instant.now().toApiDate(),
                    endDate = Instant.now().toApiDate()
                )
                getIncomeUseCase(transactionModel).collectLatest { result ->
                    result.onSuccess { income ->
                        updateSuccess(income)
                    }.onError { error ->
                        updateError(error)
                    }
                }
            }

        }
    }

    private fun updateSuccess(income: List<Transaction>) {
        _state.update {
            IncomeScreenState.Success(income.toListIncome())
        }
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            IncomeScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateLoading() {
        _state.update {
            IncomeScreenState.Loading
        }
    }

    fun retry() {
        loadIncome()
    }
}