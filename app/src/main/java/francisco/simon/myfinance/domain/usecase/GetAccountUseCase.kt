package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.repository.AccountRepository
import francisco.simon.myfinance.domain.utils.NetworkError
import francisco.simon.myfinance.domain.utils.Result
import javax.inject.Inject


class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Result<Account, NetworkError> {
        return repository.getAccount()
    }
}