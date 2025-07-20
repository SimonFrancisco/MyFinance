package francisco.simon.core.domain.repository

import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.model.TransactionResponseModel
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactions(transactionModel: TransactionModel): Flow<Result<List<Transaction>, Error>>

    suspend fun getTransactionById(transactionId:Int):Result<Transaction,Error>

    suspend fun addTransaction(transactionModel: AddTransactionModel): Result<TransactionResponseModel, Error>

    suspend fun editTransaction(transactionModel: EditTransactionModel): Result<Transaction, Error>

    suspend fun deleteTransaction(transactionId: Int): EmptyResult<Error>

    suspend fun synchronize()

}