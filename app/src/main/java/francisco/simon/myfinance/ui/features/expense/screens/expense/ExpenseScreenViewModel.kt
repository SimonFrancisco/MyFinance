package francisco.simon.myfinance.ui.features.expense.screens.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import francisco.simon.myfinance.domain.usecase.GetExpenseUseCase
import francisco.simon.myfinance.ui.features.expense.mapper.toListExpense
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ExpenseScreenViewModel @Inject constructor(
    getExpenseUseCase: GetExpenseUseCase
) : ViewModel() {

    val state = getExpenseUseCase().map {
        ExpenseScreenState.Success(it.toListExpense())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ExpenseScreenState.Loading
    )

}