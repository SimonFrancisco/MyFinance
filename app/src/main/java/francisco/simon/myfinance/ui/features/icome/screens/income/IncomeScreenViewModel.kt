package francisco.simon.myfinance.ui.features.icome.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.core.mapper.toApiDate
import francisco.simon.myfinance.core.mapper.toStringRes
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.usecase.GetIncomeUseCase
import francisco.simon.myfinance.domain.utils.onError
import francisco.simon.myfinance.domain.utils.onSuccess
import francisco.simon.myfinance.ui.features.icome.mapper.toListIncome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
    val state: StateFlow<IncomeScreenState> = _state

    init {
        loadIncome()
    }

    private fun loadIncome() {
        _state.update {
            IncomeScreenState.Loading
        }
        viewModelScope.launch {
            getAccountUseCase().onError { error ->
                val errorRes = error.toStringRes()
                _state.update {
                    IncomeScreenState.Error(errorMessageRes = errorRes)
                }
            }.onSuccess { account ->
                val transactionModel = TransactionModel(
                    accountId = account.id,
                    startDate = Instant.now().toApiDate(),
                    endDate = Instant.now().toApiDate()
                )
                getIncomeUseCase(transactionModel).collectLatest { result ->
                    result.onSuccess { income ->
                        _state.update {
                            IncomeScreenState.Success(income.toListIncome())
                        }
                    }.onError { error ->
                        val errorRes = error.toStringRes()
                        _state.update {
                            IncomeScreenState.Error(errorMessageRes = errorRes)
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