package francisco.simon.myfinance.ui.features.icome.model

import java.math.BigDecimal

data class Income(
    val transactionId: Int,
    val emoji: String,
    val name: String,
    val amount: BigDecimal,
    val currency:String,
    val comment: String? = null
)
