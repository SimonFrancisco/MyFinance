package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.utils.NetworkError
import francisco.simon.myfinance.domain.utils.Result

interface AccountRepository {
    suspend fun getAccount(): Result<Account, NetworkError>

}