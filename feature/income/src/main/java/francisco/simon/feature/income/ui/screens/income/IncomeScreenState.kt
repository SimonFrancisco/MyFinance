package francisco.simon.feature.income.ui.screens.income

import androidx.annotation.StringRes
import francisco.simon.feature.income.ui.model.IncomeUI

/**
 * All Income screen states
 * @author Simon Francisco
 */
internal sealed class IncomeScreenState {
    data object Loading : IncomeScreenState()
    data class Success(val incomeUI: List<IncomeUI>) : IncomeScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : IncomeScreenState()
}