package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.repository.TransactionRepository
import francisco.simon.myfinance.domain.utils.NetworkResult
import francisco.simon.myfinance.domain.utils.flatMapIfSuccess
import francisco.simon.myfinance.domain.utils.toSuccessResult
import javax.inject.Inject


class GetExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionModel: TransactionModel): NetworkResult<List<Transaction>> {
        return repository.getTransactions(transactionModel).flatMapIfSuccess { transactions ->
            transactions.filter { transaction ->
                !transaction.category.isIncome
            }.toSuccessResult()
        }
    }
}