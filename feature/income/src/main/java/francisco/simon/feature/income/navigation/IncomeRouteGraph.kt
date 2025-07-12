package francisco.simon.feature.income.navigation

import kotlinx.serialization.Serializable

@Serializable
data object IncomeGraph {

    @Serializable
    data object IncomeRoute

    @Serializable
    data object IncomeHistoryRoute
}
