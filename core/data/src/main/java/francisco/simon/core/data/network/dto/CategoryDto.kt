package francisco.simon.core.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Data transfer object for Category
 * @author Simon Francisco
 */
data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("emoji")
    val emoji: String,
    @SerializedName("isIncome")
    val isIncome: Boolean,
)
