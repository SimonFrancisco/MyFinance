package francisco.simon.myfinance.domain.model

data class TransactionModel(
    val accountId:Int,
    val startDate:String?,
    val endDate:String?,
)