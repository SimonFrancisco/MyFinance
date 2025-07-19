package francisco.simon.feature.expenses.ui.screens.expense

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
import francisco.simon.feature.expenses.domain.GetAccountUseCase
import francisco.simon.feature.expenses.domain.GetExpenseUseCase
import francisco.simon.feature.expenses.ui.mapper.toListExpense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

/**
 * ViewModel for expenses, works with state
 * @param getExpenseUseCase
 * @param getAccountUseCase
 * @author Simon Francisco
 */
internal class ExpenseScreenViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ExpenseScreenState> =
        MutableStateFlow(ExpenseScreenState.Loading)
    val state: StateFlow<ExpenseScreenState> = _state

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
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
                getExpenseUseCase(transactionModel).collectLatest { result ->
                    result.onSuccess { expenses ->
                        updateSuccess(expenses)
                    }.onError { error ->
                        updateError(error)
                    }
                }
            }
        }
    }

    private fun updateSuccess(expenses: List<Transaction>) {
        _state.update {
            ExpenseScreenState.Success(expenses.toListExpense())
        }
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            ExpenseScreenState.Error(errorMessageRes = errorRes)
        }
    }

    private fun updateLoading() {
        _state.update {
            ExpenseScreenState.Loading
        }
    }

    fun retry() {
        loadExpenses()
    }
}


