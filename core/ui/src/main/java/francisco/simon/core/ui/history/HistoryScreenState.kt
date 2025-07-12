package francisco.simon.core.ui.history

import androidx.annotation.StringRes
import francisco.simon.core.domain.entity.Transaction

/**
 * All states for history screen
 *
 * @author Simon Francisco
 */
sealed class HistoryScreenState {
    data object Loading : HistoryScreenState()
    data class Success(val transactions: List<Transaction>) : HistoryScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : HistoryScreenState()

}