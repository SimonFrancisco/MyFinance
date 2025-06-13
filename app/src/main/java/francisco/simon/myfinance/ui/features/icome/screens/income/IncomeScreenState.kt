package francisco.simon.myfinance.ui.features.icome.screens.income

import francisco.simon.myfinance.ui.features.icome.model.Income

sealed class IncomeScreenState {
    data object Loading : IncomeScreenState()
    data class Success(val income: List<Income>) : IncomeScreenState()
    data class Error(val errorMessage: String) : IncomeScreenState()
}