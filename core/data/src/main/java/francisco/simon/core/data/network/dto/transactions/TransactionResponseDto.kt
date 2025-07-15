package francisco.simon.core.data.network.dto.transactions

import com.google.gson.annotations.SerializedName


data class TransactionResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("accountId")
    val accountId: Int,
    @SerializedName("category")
    val categoryId: Int,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("transactionDate")
    val transactionDate: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
