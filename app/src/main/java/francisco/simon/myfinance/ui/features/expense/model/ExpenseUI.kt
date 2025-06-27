package francisco.simon.myfinance.ui.features.expense.model

import java.math.BigDecimal

/**
 * Separate UI model
 * @author Simon Francisco
 */
data class ExpenseUI(
    val transactionId: Int,
    val emoji: String,
    val name: String,
    val amount: BigDecimal,
    val currency:String,
    val comment: String? = null,
)