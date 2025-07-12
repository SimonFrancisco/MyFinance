package francisco.simon.feature.income.domain

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import javax.inject.Inject


internal class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Result<Account, Error> {
        return repository.getAccount()
    }
}