package francisco.simon.core.data.network.repositories

import francisco.simon.core.data.network.api.ApiClient
import francisco.simon.core.data.network.api.ApiService
import francisco.simon.core.data.network.mappers.toAccount
import francisco.simon.core.data.network.mappers.toAccountUpdateRequestDto
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of Account repository, operations happen in Dispatcher IO
 * @param apiService
 * @param apiClient
 *
 * @author Simon Francisco
 */
class AccountRepositoryImpl (
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