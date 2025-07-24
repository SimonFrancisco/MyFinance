package francisco.simon.core.domain.model

data class CategoryStatsModel(
    val categoryId:Int,
    val name: String,
    val emoji: String,
    val percent: Float,
    val currency: String,
    val amount: String
)
