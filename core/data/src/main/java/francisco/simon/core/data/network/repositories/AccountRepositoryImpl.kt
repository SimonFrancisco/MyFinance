package francisco.simon.core.data.network.repositories

import francisco.simon.core.data.local.account.db.AccountDao
import francisco.simon.core.data.local.account.mappers.toAccount
import francisco.simon.core.data.local.account.mappers.toDbModel
import francisco.simon.core.data.local.transactions.db.TransactionDao
import francisco.simon.core.data.network.api.ApiClient
import francisco.simon.core.data.network.api.ApiService
import francisco.simon.core.data.network.dto.account.AccountByIdDto
import francisco.simon.core.data.network.dto.account.AccountDto
import francisco.simon.core.data.network.mappers.toAccount
import francisco.simon.core.data.network.mappers.toAccountUpdateRequestDto
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of Account repository, operations happen in Dispatcher IO
 * Network-first, fallback to DB
 * @param apiService
 * @param apiClient
 * @author Simon Francisco
 */
class AccountRepositoryImpl(
    private val apiService: ApiService,
    private val apiClient: ApiClient,
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) : AccountRepository {

    override suspend fun getAccount(): Result<Account, Error> {
        synchronize()
        return withContext(Dispatchers.IO) {
            try {
                when (val apiResult = apiClient.safeApiCall { apiService.getAccounts() }) {
                    is Result.Error<NetworkError> -> {
                        val localAccount = accountDao.getAccount()
                        Result.Success(localAccount.toAccount())
                    }
                    is Result.Success<List<AccountDto>> -> {
                        accountDao.insertAccount(
                            apiResult.data.first().toDbModel(isSynchronized = true)
                        )
                        Result.Success(apiResult.data.first().toAccount())
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }

        }
    }

    override suspend fun getAccountById(accountId: Int): Result<Account, Error> {
        synchronize() // TODO maybe this is extra here and must be removed
        return withContext(Dispatchers.IO) {
            try {
                when (val apiResult =
                    apiClient.safeApiCall { apiService.getAccountById(accountId) }) {
                    is Result.Error<NetworkError> -> {
                        val localAccount = accountDao.getAccount()
                        Result.Success(localAccount.toAccount())
                    }

                    is Result.Success<AccountByIdDto> -> {
                        accountDao.insertAccount(
                            apiResult.data.toDbModel(isSynchronized = true)
                        )
                        Result.Success(apiResult.data.toAccount())
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }
        }

    }

    override suspend fun updateAccount(updateAccountBody: AccountUpdateRequestModel): Result<Account, Error> {
        return withContext(Dispatchers.IO) {
            try {
                when (val apiResult = apiClient.safeApiCall {
                    apiService.updateAccount(
                        accountId = updateAccountBody.id,
                        updateAccountBody = updateAccountBody.toAccountUpdateRequestDto()
                    )
                }) {
                    is Result.Error<NetworkError> -> {
                        val localAccount = accountDao.getAccount().copy(
                            name = updateAccountBody.name,
                            balance = updateAccountBody.balance,
                            currency = updateAccountBody.currency,
                            isSynchronized = false
                        )
                        accountDao.updateAccount(localAccount)
                        propagateAccountUpdateToLocalTransactions(updateAccountBody)
                        Result.Success(localAccount.toAccount())

                    }
                    is Result.Success<AccountDto> -> {
                        val localAccount = accountDao.getAccount().copy(
                            name = apiResult.data.name,
                            balance = apiResult.data.balance,
                            currency = apiResult.data.currency,
                            isSynchronized = true
                        )
                        accountDao.updateAccount(localAccount)
                        propagateAccountUpdateToLocalTransactions(updateAccountBody)
                        Result.Success(apiResult.data.toAccount())
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }


        }
    }

    private suspend fun propagateAccountUpdateToLocalTransactions(updateAccountBody: AccountUpdateRequestModel) {
        transactionDao.updateTransactionsForAccountUpdate(
            accountId = updateAccountBody.id,
            newAccountName = updateAccountBody.name,
            newAccountBalance = updateAccountBody.balance,
            newAccountCurrency = updateAccountBody.currency
        )
    }

    override suspend fun synchronize() {
        withContext(Dispatchers.IO) {
            if (accountDao.countAccounts() == 0) {
                return@withContext
            }
            val localAccount = accountDao.getAccount()
            if (localAccount.isSynchronized) {
                return@withContext
            } else {
                val accountUpdateRequestModel = AccountUpdateRequestModel(
                    id = localAccount.id,
                    name = localAccount.name,
                    balance = localAccount.balance,
                    currency = localAccount.currency
                )
                updateAccount(accountUpdateRequestModel)
            }

        }
    }
}