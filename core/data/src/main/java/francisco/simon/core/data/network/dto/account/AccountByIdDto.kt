package francisco.simon.core.data.network.dto.account

import com.google.gson.annotations.SerializedName
import francisco.simon.core.data.network.dto.StateItemDto

/**
 * Data transfer object for Account by ID
 * @author Simon Francisco
 */
// TODO rename this
data class AccountByIdDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("incomeStats")
    val incomeStats: List<StateItemDto>,
    @SerializedName("expenseStats")
    val expenseStats: List<StateItemDto>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
)