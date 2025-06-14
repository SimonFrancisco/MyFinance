package francisco.simon.myfinance.ui.features.icome.model

data class Income(
    val transactionId: Int,
    val name: String,
    val amount: String,
    val currency:String,
    val comment: String? = null
)
