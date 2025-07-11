package francisco.simon.feature.expenses.ui.model

import java.math.BigDecimal

/**
 * Separate UI model
 * @author Simon Francisco
 */
internal data class ExpenseUI(
    val transactionId: Int,
    val emoji: String,
    val name: String,
    val amount: BigDecimal,
    val currency:String,
    val comment: String? = null,
)