package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactions(transactionModel: TransactionModel): Flow<Result<List<Transaction>, Error>>

}