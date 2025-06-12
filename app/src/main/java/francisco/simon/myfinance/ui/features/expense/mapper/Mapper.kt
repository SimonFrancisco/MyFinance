package francisco.simon.myfinance.ui.features.expense.mapper

import francisco.simon.myfinance.core.mapper.toVectorRes
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.ui.features.expense.model.Expense

fun Transaction.toExpense(): Expense {
    return Expense(
        transactionId = this.id,
        emojiRes = this.category.emoji.toVectorRes(),
        name = this.category.name,
        amount = this.amount,
        comment = this.comment
    )
}

fun List<Transaction>.toListExpense(): List<Expense> {
    return this.map { transaction ->
        transaction.toExpense()
    }
}