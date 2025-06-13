package francisco.simon.myfinance.ui.features.icome.mapper

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.ui.features.icome.model.Income

fun Transaction.toIncome(): Income {
    return Income(
        transactionId = this.id,
        name = this.category.name,
        amount = this.amount,
        comment = this.comment,
        currency = this.account.currency
    )
}

fun List<Transaction>.toListIncome(): List<Income> {
    return this.map { transaction ->
        transaction.toIncome()
    }
}