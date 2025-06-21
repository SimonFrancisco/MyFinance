package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.repository.TransactionRepository
import francisco.simon.myfinance.domain.utils.NetworkResult
import javax.inject.Inject


class GetAccountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): NetworkResult<Account> {
        return repository.getAccount()
    }
}