package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: Int): Result<Account, Error> {
        return repository.getAccountById(accountId)
    }
}