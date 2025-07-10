package francisco.simon.myfinance.ui.features.account.mapper

import francisco.simon.core.domain.entity.Account
import francisco.simon.myfinance.ui.features.account.model.AccountUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */
fun Account.toAccountUI(): AccountUI {
    return AccountUI(
        id = id,
        name = name,
        balance = balance.toBigDecimal(),
        currency = currency,
    )
}