package francisco.simon.myfinance.ui.features.income.model

import java.math.BigDecimal

data class IncomeUI(
    val transactionId: Int,
    val emoji: String,
    val name: String,
    val amount: BigDecimal,
    val currency:String,
    val comment: String? = null
)
