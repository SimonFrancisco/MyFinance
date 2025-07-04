package francisco.simon.myfinance.data.repositories

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.core.domain.utils.map
import francisco.simon.myfinance.data.api.ApiClient
import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.data.mappers.toAccount
import francisco.simon.myfinance.data.mappers.toAccountUpdateRequestDto
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.model.AccountUpdateRequestModel
import francisco.simon.myfinance.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of Account repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 * @author Simon Francisco
 */
class AccountRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiClient: ApiClient,
) : AccountRepository {

    override suspend fun getAccount(): Result<Account, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall { apiService.getAccounts() }.map { listAccountsDto ->
                listAccountsDto.first().toAccount()
            }
        }
    }

    override suspend fun getAccountById(accountId: Int): Result<Account, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall { apiService.getAccountById(accountId) }.map { accountByIdDto ->
                accountByIdDto.toAccount()
            }
        }
    }

    override suspend fun updateAccount(updateAccountBody: AccountUpdateRequestModel): Result<Account, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall { apiService.updateAccount(
                accountId = updateAccountBody.id,
                updateAccountBody = updateAccountBody.toAccountUpdateRequestDto()
            ) }.map { accountDto ->
                accountDto.toAccount()
            }
        }
    }
}