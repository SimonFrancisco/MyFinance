package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result

interface AccountRepository {
    suspend fun getAccount(): Result<Account, Error>

}