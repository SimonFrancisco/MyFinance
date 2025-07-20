package francisco.simon.core.data.local.transactions.model

data class CategoryTransaction(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val isIncome: Boolean,
    )
