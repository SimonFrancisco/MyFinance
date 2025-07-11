package francisco.simon.feature.account.domain

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import javax.inject.Inject

internal class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: Int): Result<Account, Error> {
        return repository.getAccountById(accountId)
    }
}