package francisco.simon.myfinance.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdateRequestModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)