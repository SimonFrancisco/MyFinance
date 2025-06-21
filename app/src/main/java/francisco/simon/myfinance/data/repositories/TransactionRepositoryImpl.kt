package francisco.simon.myfinance.data.repositories

import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.data.api.safeApiCall
import francisco.simon.myfinance.data.mappers.toAccount
import francisco.simon.myfinance.data.mappers.toTransaction
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.repository.TransactionRepository
import francisco.simon.myfinance.domain.utils.NetworkResult
import francisco.simon.myfinance.domain.utils.flatMapIfSuccess
import francisco.simon.myfinance.domain.utils.toSuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun getTransactions(
        transactionModel: TransactionModel
    ): NetworkResult<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                apiService.getTransactions(
                    accountId = transactionModel.accountId,
                    startDate = transactionModel.startDate,
                    endDate = transactionModel.endDate
                )
            }.flatMapIfSuccess { listDto ->
                listDto.map {
                    it.toTransaction()
                }.toSuccessResult()
            }
        }

    }

    override suspend fun getAccount(): NetworkResult<Account> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                apiService.getAccounts()
            }.flatMapIfSuccess {
                it.first().toAccount().toSuccessResult()
            }
        }

    }
}