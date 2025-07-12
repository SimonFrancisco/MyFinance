package francisco.simon.feature.account.ui.screens.edit

import androidx.annotation.StringRes
import francisco.simon.feature.account.ui.model.AccountUI


internal sealed class AccountEditScreenState {
    data object Loading : AccountEditScreenState()
    data class Success(val account: AccountUI) : AccountEditScreenState()
    data class Error(@StringRes val errorMessageRes: Int) : AccountEditScreenState()
}