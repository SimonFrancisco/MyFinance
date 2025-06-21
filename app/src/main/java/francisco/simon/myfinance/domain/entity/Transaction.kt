package francisco.simon.myfinance.domain.entity

data class Transaction(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val comment: String,
    val transactionDate: String,
    val createdAt: String,
    val updatedAt: String
)


