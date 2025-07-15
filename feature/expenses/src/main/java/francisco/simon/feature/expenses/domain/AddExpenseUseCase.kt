package francisco.simon.feature.expenses.domain

import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.TransactionResponse
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import javax.inject.Inject

internal class AddExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionModel: AddTransactionModel): Result<TransactionResponse, Error> {
        return repository.addTransaction(transactionModel)
    }
}