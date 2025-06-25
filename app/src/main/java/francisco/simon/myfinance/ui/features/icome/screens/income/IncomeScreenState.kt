package francisco.simon.myfinance.ui.features.icome.screens.income

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.icome.model.Income

sealed class IncomeScreenState {
    data object Loading : IncomeScreenState()
    data class Success(val income: List<Income>) : IncomeScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : IncomeScreenState()
}