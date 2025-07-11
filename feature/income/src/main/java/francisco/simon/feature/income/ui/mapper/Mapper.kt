package francisco.simon.feature.income.ui.mapper

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.feature.income.ui.model.IncomeUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */
internal fun Transaction.toIncome(): IncomeUI {
    return IncomeUI(
        transactionId = this.id,
        name = this.category.name,
        amount = this.amount.toBigDecimal(),
        comment = this.comment,
        currency = this.account.currency,
        emoji = this.category.emoji
    )
}

internal fun List<Transaction>.toListIncome(): List<IncomeUI> {
    return this.map { transaction ->
        transaction.toIncome()
    }
}