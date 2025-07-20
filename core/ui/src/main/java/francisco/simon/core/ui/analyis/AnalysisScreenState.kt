package francisco.simon.core.ui.analyis

import androidx.annotation.StringRes
import francisco.simon.core.domain.model.CategoryStatsModel

/**
 * All states for history screen
 *
 * @author Simon Francisco
 */
sealed class AnalysisScreenState {
    data object Loading : AnalysisScreenState()
    data object Empty: AnalysisScreenState()
    data class Success(val categories: List<CategoryStatsModel>) : AnalysisScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : AnalysisScreenState()

}