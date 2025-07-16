package francisco.simon.core.data.network.repositories

import francisco.simon.core.data.network.api.ApiClient
import francisco.simon.core.data.network.api.ApiService
import francisco.simon.core.data.network.mappers.toAddTransactionDto
import francisco.simon.core.data.network.mappers.toEditTransactionDtoModel
import francisco.simon.core.data.network.mappers.toTransaction
import francisco.simon.core.data.network.mappers.toTransactionResponse
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.model.TransactionResponseModel
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Implementation of Transaction repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 * @author Simon Francisco
 */
class TransactionRepositoryImpl(
    private val apiService: ApiService,
    private val apiClient: ApiClient
) : TransactionRepository {

    override suspend fun getTransactions(
        transactionModel: TransactionModel
    ): Flow<Result<List<Transaction>, Error>> {
        return flow {
            val result = apiClient.safeApiCall {
                apiService.getTransactions(
                    accountId = transactionModel.accountId,
                    startDate = transactionModel.startDate,
                    endDate = transactionModel.endDate
                )
            }.map { listTransactionsDto ->
                listTransactionsDto.sortedByDescending { it.transactionDate }
                    .map { transactionDto ->
                    transactionDto.toTransaction()
                }
            }
            emit(result)

        }.flowOn(Dispatchers.IO)

    }

    override suspend fun getTransactionById(transactionId: Int): Result<Transaction, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall {
                apiService.getTransactionById(transactionId)
            }.map { transactionDto ->
                transactionDto.toTransaction()
            }
        }
    }

    override suspend fun addTransaction(transactionModel: AddTransactionModel): Result<TransactionResponseModel, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall {
                apiService.addTransaction(
                    transactionModel.toAddTransactionDto()
                )
            }.map { transactionDto ->
                transactionDto.toTransactionResponse()
            }
        }
    }

    override suspend fun editTransaction(transactionModel: EditTransactionModel): Result<Transaction, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall {
                apiService.editTransaction(
                    transactionId = transactionModel.transactionId,
                    editTransactionDto = transactionModel.toEditTransactionDtoModel()
                )
            }.map { transactionDto ->
                transactionDto.toTransaction()
            }
        }
    }

    override suspend fun deleteTransaction(transactionId: Int): EmptyResult<Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall {
                apiService.deleteTransaction(transactionId)
            }
        }
    }
}