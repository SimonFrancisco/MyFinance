package francisco.simon.core.data.network.dto.account

import com.google.gson.annotations.SerializedName

data class AccountUpdateRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("currency")
    val currency: String
)