package francisco.simon.myfinance.domain.entity

/**
 * Transaction contains other 2 data classes
 * @see Account
 * @see Category
 * @author Simon Francisco
 */
data class Transaction(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val comment: String?,
    val transactionDate: String,
    val createdAt: String,
    val updatedAt: String
)


