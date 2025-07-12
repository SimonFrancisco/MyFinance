package francisco.simon.core.domain.model


data class AccountUpdateRequestModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)