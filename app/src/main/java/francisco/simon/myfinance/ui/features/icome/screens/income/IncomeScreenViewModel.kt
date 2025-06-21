package francisco.simon.myfinance.ui.features.icome.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.mapper.toApiDate
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.usecase.GetIncomeUseCase
import francisco.simon.myfinance.domain.utils.NetworkResult
import francisco.simon.myfinance.ui.features.icome.mapper.toListIncome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class IncomeScreenViewModel @Inject constructor(
    private val getIncomeUseCase: GetIncomeUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<IncomeScreenState> =
        MutableStateFlow(IncomeScreenState.Loading)
    val state = _state

    init {
        loadIncome()
    }

    private fun loadIncome() {
        viewModelScope.launch {
            when (val account = getAccountUseCase()) {
                is NetworkResult.Error -> {
                    _state.update {
                        IncomeScreenState.Error(account.message)
                    }
                }

                is NetworkResult.Exception -> {
                    _state.update {
                        IncomeScreenState.Error(account.e.message.toString())
                    }
                }

                is NetworkResult.Success<Account> -> {
                    val transactionModel = TransactionModel(
                        accountId = account.data.id,
                        startDate = Instant.now().toApiDate(),
                        endDate = Instant.now().toApiDate()
                    )
                    when (val income = getIncomeUseCase(transactionModel)) {
                        is NetworkResult.Error -> {
                            _state.update {
                                IncomeScreenState.Error(income.message)
                            }
                        }

                        is NetworkResult.Exception -> {
                            _state.update {
                                IncomeScreenState.Error(income.e.message.toString())
                            }
                        }

                        is NetworkResult.Success<List<Transaction>> -> {
                            _state.update {
                                IncomeScreenState.Success(income.data.toListIncome())
                            }
                        }
                    }
                }
            }


        }
    }

    fun retry() {
        loadIncome()
    }
}