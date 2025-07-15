package francisco.simon.core.domain.model


data class TransactionResponse(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val comment: String?,
    val transactionDate: String,
    val createdAt: String,
    val updatedAt: String
)
