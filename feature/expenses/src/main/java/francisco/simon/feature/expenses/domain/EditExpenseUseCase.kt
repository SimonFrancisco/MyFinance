package francisco.simon.feature.expenses.domain

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import javax.inject.Inject

internal class EditExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionModel: EditTransactionModel): Result<Transaction, Error> {
        return repository.editTransaction(transactionModel)
    }
}