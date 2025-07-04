package francisco.simon.myfinance.ui.features.account.screens.account

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.account.model.AccountUI

/**
 * All Account screen states
 * @author Simon Francisco
 */
sealed class AccountScreenState {
    data object Loading : AccountScreenState()
    data class Success(val account: AccountUI) : AccountScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : AccountScreenState()
}