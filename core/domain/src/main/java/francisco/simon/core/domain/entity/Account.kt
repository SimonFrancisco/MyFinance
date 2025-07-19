package francisco.simon.core.domain.entity


data class Account(
    val accountId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StateItem>? = null,
    val expenseStats: List<StateItem>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
