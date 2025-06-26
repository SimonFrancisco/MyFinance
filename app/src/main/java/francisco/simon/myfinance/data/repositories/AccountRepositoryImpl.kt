package francisco.simon.myfinance.data.repositories

import francisco.simon.myfinance.data.api.ApiClient
import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.data.mappers.toAccount
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.repository.AccountRepository
import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.core.domain.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiClient: ApiClient,
) : AccountRepository {

    override suspend fun getAccount(): Result<Account, Error> {
        return withContext(Dispatchers.IO) {
            apiClient.safeApiCall { apiService.getAccounts() }.map {listAccountsDto->
                listAccountsDto.first().toAccount()
            }
        }
    }
}