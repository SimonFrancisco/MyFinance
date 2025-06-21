package francisco.simon.myfinance.ui.features.expense.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.mapper.toApiDate
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.usecase.GetExpenseUseCase
import francisco.simon.myfinance.domain.utils.NetworkResult
import francisco.simon.myfinance.ui.features.expense.mapper.toListExpense
import francisco.simon.myfinance.ui.features.expense.screens.expense.ExpenseScreenState
import kotlinx.coroutines.flow.MutableStateFlow
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
    val state = _state

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            when (val account = getAccountUseCase()) {
                is NetworkResult.Error -> {
                    _state.update {
                        ExpenseScreenState.Error(account.message)
                    }
                }

                is NetworkResult.Exception -> {
                    _state.update {
                        ExpenseScreenState.Error(account.e.message.toString())
                    }
                }

                is NetworkResult.Success<Account> -> {
                    val transactionModel = TransactionModel(
                        accountId = account.data.id,
                        startDate = Instant.now().toApiDate(),
                        endDate = Instant.now().toApiDate()
                    )
                    when (val expense = getExpenseUseCase(transactionModel)) {
                        is NetworkResult.Error -> {
                            _state.update {
                                ExpenseScreenState.Error(expense.message)
                            }
                        }

                        is NetworkResult.Exception -> {
                            _state.update {
                                ExpenseScreenState.Error(expense.e.message.toString())
                            }
                        }

                        is NetworkResult.Success<List<Transaction>> -> {
                            _state.update {
                                ExpenseScreenState.Success(expense.data.toListExpense())
                            }
                        }
                    }
                }
            }


        }
    }
}
