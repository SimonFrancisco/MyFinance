package francisco.simon.core.data.local.transactions.model

data class AccountTransaction(
    val accountId: Int,
    val accountName: String,
    val balance: String,
    val currency: String,
)
