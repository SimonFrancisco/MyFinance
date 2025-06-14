package francisco.simon.myfinance.ui.features.expense.model

import androidx.annotation.DrawableRes

data class Expense(
    val transactionId: Int,
    @DrawableRes val emojiRes: Int,
    val name: String,
    val amount: String,
    val currency:String,
    val comment: String? = null
)