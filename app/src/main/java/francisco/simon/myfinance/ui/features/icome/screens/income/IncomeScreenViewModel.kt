package francisco.simon.myfinance.ui.features.icome.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.usecase.GetIncomeUseCase
import francisco.simon.myfinance.ui.features.expense.screens.expense.ExpenseScreenState
import francisco.simon.myfinance.ui.features.icome.mapper.toListIncome
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class IncomeScreenViewModel @Inject constructor(
    getIncomeUseCase: GetIncomeUseCase
) : ViewModel() {
    val state = getIncomeUseCase().map {
        IncomeScreenState.Success(it.toListIncome())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = IncomeScreenState.Loading
    )
}