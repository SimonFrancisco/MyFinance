package francisco.simon.myfinance.core.ui.history

import androidx.annotation.StringRes
import francisco.simon.myfinance.domain.entity.Transaction

sealed class HistoryScreenState {
    data object Loading : HistoryScreenState()
    data class Success(val transactions: List<Transaction>) : HistoryScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : HistoryScreenState()

}