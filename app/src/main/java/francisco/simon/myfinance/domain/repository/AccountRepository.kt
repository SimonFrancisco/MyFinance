package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.model.AccountUpdateRequestModel

interface AccountRepository {
    suspend fun getAccount(): Result<Account, Error>

    suspend fun getAccountById(accountId: Int): Result<Account, Error>

    suspend fun updateAccount(
        updateAccountBody: AccountUpdateRequestModel
    ): Result<Account, Error>
}