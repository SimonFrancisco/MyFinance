package francisco.simon.myfinance.ui.features.account.screens.edit

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.account.model.AccountUI


sealed class AccountEditScreenState {
    data object Loading : AccountEditScreenState()
    data class Success(val account: AccountUI) : AccountEditScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : AccountEditScreenState()
}