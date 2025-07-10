package francisco.simon.core.data.dto

import com.google.gson.annotations.SerializedName

data class StateItemDto(
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("emoji")
    val emoji: String,
    @SerializedName("amount")
    val amount: String
)
