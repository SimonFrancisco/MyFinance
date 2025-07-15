package francisco.simon.core.domain.model

data class EditTransactionModel(
    val transactionId: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String? = null
)
