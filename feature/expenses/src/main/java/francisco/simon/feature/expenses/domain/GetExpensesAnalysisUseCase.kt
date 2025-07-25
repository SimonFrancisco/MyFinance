package francisco.simon.feature.expenses.domain

import francisco.simon.core.domain.model.CategoryStatsModel
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class GetExpensesAnalysisUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionModel: TransactionModel): Flow<Result<List<CategoryStatsModel>, Error>> {
        return repository.getTransactions(transactionModel).map { result ->
            result.map { transactions ->
                val expenses = transactions.filter { transaction ->
                    !transaction.category.isIncome
                }
                val totalSum = expenses.sumOf {
                    it.amount.toBigDecimal()
                }
                val transactionsGrouped = expenses.groupBy {
                    it.category
                }
                var currency = ""
                transactionsGrouped.map { (category, groupedTransaction) ->
                    val amount = groupedTransaction.fold(BigDecimal.ZERO) { acc, transaction ->
                        currency = transaction.account.currency
                        acc + transaction.amount.toBigDecimal()
                    }
                    val percent = (amount.toFloat() / totalSum.toFloat()) * 100
                    CategoryStatsModel(
                        categoryId = category.id,
                        name = category.name,
                        emoji = category.emoji,
                        percent = percent,
                        amount = amount.setScale(2, RoundingMode.HALF_UP).toString(),
                        currency = currency
                    )
                }
            }
        }
    }
}