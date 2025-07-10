package francisco.simon.myfinance.ui.features.income.mapper

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.myfinance.ui.features.income.model.IncomeUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */
fun Transaction.toIncome(): IncomeUI {
    return IncomeUI(
        transactionId = this.id,
        name = this.category.name,
        amount = this.amount.toBigDecimal(),
        comment = this.comment,
        currency = this.account.currency,
        emoji = this.category.emoji
    )
}

fun List<Transaction>.toListIncome(): List<IncomeUI> {
    return this.map { transaction ->
        transaction.toIncome()
    }
}