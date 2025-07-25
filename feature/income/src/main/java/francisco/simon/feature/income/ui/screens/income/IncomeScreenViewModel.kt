package francisco.simon.feature.income.ui.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.onError
import francisco.simon.core.domain.utils.onSuccess
import francisco.simon.core.ui.utils.toApiDate
import francisco.simon.core.ui.utils.toStringRes
import francisco.simon.feature.income.domain.GetAccountUseCase
import francisco.simon.feature.income.domain.GetIncomeUseCase
import francisco.simon.feature.income.ui.mapper.toListIncome
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
internal class IncomeScreenViewModel @Inject constructor(
    private val getIncomeUseCase: GetIncomeUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<IncomeScreenState> =
        MutableStateFlow(IncomeScreenState.Loading)
    val state: StateFlow<IncomeScreenState> = _state


    fun loadIncome() {
        updateLoading()
        viewModelScope.launch {
            getAccountUseCase().onError { error ->
                updateError(error)
            }.onSuccess { account ->
                val transactionModel = TransactionModel(
                    accountId = account.accountId,
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