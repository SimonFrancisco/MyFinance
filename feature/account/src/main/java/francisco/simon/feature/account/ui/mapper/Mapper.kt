package francisco.simon.feature.account.ui.mapper

import francisco.simon.core.domain.entity.Account
import francisco.simon.feature.account.ui.model.AccountUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */
internal fun Account.toAccountUI(): AccountUI {
    return AccountUI(
        id = accountId,
        name = name,
        balance = balance.toBigDecimal(),
        currency = currency,
    )
}