package francisco.simon.feature.account.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AccountGraph {

    @Serializable
    data object AccountRoute

    @Serializable
    data class AccountEditRoute(
        val accountId: Int
    )
}