package francisco.simon.myfinance.domain.usecase

import francisco.simon.myfinance.core.domain.utils.Error
import francisco.simon.myfinance.core.domain.utils.Result
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.model.AccountUpdateRequestModel
import francisco.simon.myfinance.domain.repository.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(
        updateAccountBody: AccountUpdateRequestModel
    ): Result<Account, Error> {
        return repository.updateAccount(updateAccountBody)
    }
}