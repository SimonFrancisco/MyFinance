package francisco.simon.core.data.network.dto.transactions

import com.google.gson.annotations.SerializedName

data class AddTransactionDto(
    @SerializedName("accountId")
    val accountId: Int,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("transactionDate")
    val transactionDate: String,
    @SerializedName("comment")
    val comment: String? = null
)
