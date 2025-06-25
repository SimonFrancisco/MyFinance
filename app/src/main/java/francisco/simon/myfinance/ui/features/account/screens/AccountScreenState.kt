package francisco.simon.myfinance.ui.features.account.screens

import androidx.annotation.StringRes
import francisco.simon.myfinance.ui.features.account.model.AccountUI

sealed class AccountScreenState{
    data object Loading:AccountScreenState()
    data class Success(val account: AccountUI):AccountScreenState()
    data class Error(@StringRes val errorMessageRes: Int):AccountScreenState()
}