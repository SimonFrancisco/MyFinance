package francisco.simon.core.domain.model

/**
 * This model keeps short the parameters to get transactions for a certain period
 * @author Simon Francisco
 */
data class TransactionModel(
    val accountId:Int,
    val startDate:String,
    val endDate:String,
)