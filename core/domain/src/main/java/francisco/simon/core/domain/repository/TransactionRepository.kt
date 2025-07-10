package francisco.simon.core.domain.repository

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactions(transactionModel: TransactionModel): Flow<Result<List<Transaction>, Error>>

}