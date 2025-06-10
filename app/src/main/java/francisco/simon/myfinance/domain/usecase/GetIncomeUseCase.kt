package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetIncomeUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(): Flow<List<Transaction>> {
        return repository.getTransactions().map { transactions ->
            transactions.filter { transaction ->
                transaction.category.isIncome
            }
        }
    }
}