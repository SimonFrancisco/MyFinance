package francisco.simon.myfinance.domain.usecase

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Get transactions and filters income out
 * @author Simon Francisco
 */
class GetExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionModel: TransactionModel): Flow<Result<List<Transaction>, Error>> {
        return repository.getTransactions(transactionModel).map { result ->
            result.map { transactions ->
                transactions.filter { transaction ->
                    !transaction.category.isIncome
                }
            }
        }
    }
}