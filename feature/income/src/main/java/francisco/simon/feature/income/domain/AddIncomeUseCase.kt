package francisco.simon.feature.income.domain

import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.TransactionResponseModel
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import javax.inject.Inject

internal class AddIncomeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionModel: AddTransactionModel): Result<TransactionResponseModel, Error> {
        return repository.addTransaction(transactionModel)
    }
}