package francisco.simon.myfinance.ui.features.expense.mapper

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.ui.features.expense.model.ExpenseUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */
fun Transaction.toExpense(): ExpenseUI {
    return ExpenseUI(
        transactionId = this.id,
        emoji = this.category.emoji,
        name = this.category.name,
        amount = this.amount.toBigDecimal(),
        comment = this.comment,
        currency = this.account.currency,
    )
}

fun List<Transaction>.toListExpense(): List<ExpenseUI> {
    return this.map { transaction ->
        transaction.toExpense()
    }
}