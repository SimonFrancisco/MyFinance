package francisco.simon.myfinance.ui.features.expense.mapper

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.ui.features.expense.model.Expense

fun Transaction.toExpense(): Expense {
    return Expense(
        transactionId = this.id,
        emoji = this.category.emoji,
        name = this.category.name,
        amount = this.amount.toBigDecimal(),
        comment = this.comment,
        currency = this.account.currency,
    )
}

fun List<Transaction>.toListExpense(): List<Expense> {
    return this.map { transaction ->
        transaction.toExpense()
    }
}