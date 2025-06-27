package francisco.simon.myfinance.ui.features.income.screens.income

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.income.model.IncomeUI

sealed class IncomeScreenState {
    data object Loading : IncomeScreenState()
    data class Success(val incomeUI: List<IncomeUI>) : IncomeScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : IncomeScreenState()
}