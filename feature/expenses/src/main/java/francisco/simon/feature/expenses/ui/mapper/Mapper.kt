package francisco.simon.feature.expenses.ui.mapper

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.feature.expenses.ui.model.ExpenseUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */
internal fun Transaction.toExpense(): ExpenseUI {
    return ExpenseUI(
        transactionId = this.id,
        emoji = this.category.emoji,
        name = this.category.name,
        amount = this.amount.toBigDecimal(),
        comment = this.comment,
        currency = this.account.currency,
    )
}

internal fun List<Transaction>.toListExpense(): List<ExpenseUI> {
    return this.map { transaction ->
        transaction.toExpense()
    }
}