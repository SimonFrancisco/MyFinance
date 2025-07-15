package francisco.simon.core.data.network.dto.transactions

import com.google.gson.annotations.SerializedName
import francisco.simon.core.data.network.dto.account.AccountDto
import francisco.simon.core.data.network.dto.CategoryDto

/**
 * Data transfer object for Transaction
 * @author Simon Francisco
 */
data class TransactionDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("account")
    val account: AccountDto,
    @SerializedName("category")
    val category: CategoryDto,
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