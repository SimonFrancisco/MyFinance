package francisco.simon.myfinance.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Data transfer object for Account
 * @author Simon Francisco
 */
// TODO rename this
data class AccountDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)

