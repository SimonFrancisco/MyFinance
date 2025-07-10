package francisco.simon.core.data.repositories

import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.map
import francisco.simon.core.data.data_source.network.api.ApiClient
import francisco.simon.core.data.data_source.network.api.ApiService
import francisco.simon.core.data.mappers.toTransaction
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Implementation of Transaction repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 * @author Simon Francisco
 */
class TransactionRepositoryImpl (
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
                listTransactionsDto.map { transactionDto ->
                    transactionDto.toTransaction()
                }
            }
            emit(result)

        }.flowOn(Dispatchers.IO)

    }
}