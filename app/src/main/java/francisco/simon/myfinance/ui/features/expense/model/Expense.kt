package francisco.simon.myfinance.ui.features.expense.model

import java.math.BigDecimal

data class Expense(
    val transactionId: Int,
    val emoji: String,
    val name: String,
    val amount: BigDecimal,
    val currency:String,
    val comment: String? = null,
)