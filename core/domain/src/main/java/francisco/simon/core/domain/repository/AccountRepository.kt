package francisco.simon.core.domain.repository

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result

interface AccountRepository {
    suspend fun getAccount(): Result<Account, Error>

    suspend fun getAccountById(accountId: Int): Result<Account, Error>

    suspend fun updateAccount(
        updateAccountBody: AccountUpdateRequestModel
    ): Result<Account, Error>
}