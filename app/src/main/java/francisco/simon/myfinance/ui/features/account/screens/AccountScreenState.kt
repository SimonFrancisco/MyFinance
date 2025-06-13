package francisco.simon.myfinance.ui.features.account.screens

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.ui.features.account.model.AccountUI

sealed class AccountScreenState{
    data object Loading:AccountScreenState()
    data class Success(val account: AccountUI):AccountScreenState()
    data class Error(val message:String):AccountScreenState()
}