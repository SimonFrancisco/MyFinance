package francisco.simon.myfinance.ui.features.account.mapper

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.ui.features.account.model.AccountUI

fun Account.toAccountUI(): AccountUI {
    return AccountUI(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}