package francisco.simon.myfinance.ui.features.expense.screens.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.mapper.toApiDate
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.usecase.GetExpenseUseCase
import francisco.simon.myfinance.domain.utils.NetworkError
import francisco.simon.myfinance.domain.utils.onError
import francisco.simon.myfinance.domain.utils.onSuccess
import francisco.simon.myfinance.ui.features.expense.mapper.toListExpense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class ExpenseScreenViewModel @Inject constructor(
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
        _state.update {
            ExpenseScreenState.Loading
        }
        viewModelScope.launch {
            getAccountUseCase().onError { error ->
                val errorRes = (error as NetworkError).toStringRes()
                _state.update {
                    ExpenseScreenState.Error(errorMessageRes = errorRes)
                }
            }.onSuccess { account ->
                val transactionModel = TransactionModel(
                    accountId = account.id,
                    startDate = Instant.now().toApiDate(),
                    endDate = Instant.now().toApiDate()
                )
                getExpenseUseCase(transactionModel).collectLatest { result ->
                    result.onSuccess { expenses ->
                        _state.update {
                            ExpenseScreenState.Success(expenses.toListExpense())
                        }
                    }.onError { error ->
                        val errorRes = (error as NetworkError).toStringRes()
                        _state.update {
                            ExpenseScreenState.Error(errorMessageRes = errorRes)
                        }
                    }

                }
            }

        }

    }

    fun retry() {
        loadExpenses()
    }
}


