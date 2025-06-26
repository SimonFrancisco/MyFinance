package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.core.domain.utils.map
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


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