package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.utils.NetworkError
import francisco.simon.myfinance.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactions(transactionModel: TransactionModel): Flow<Result<List<Transaction>, NetworkError>>

}