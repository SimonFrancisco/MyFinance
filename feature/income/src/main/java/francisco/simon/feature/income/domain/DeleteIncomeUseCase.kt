package francisco.simon.feature.income.domain

import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import javax.inject.Inject

internal class DeleteIncomeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId:Int):EmptyResult<Error>{
        return repository.deleteTransaction(transactionId)
    }
}