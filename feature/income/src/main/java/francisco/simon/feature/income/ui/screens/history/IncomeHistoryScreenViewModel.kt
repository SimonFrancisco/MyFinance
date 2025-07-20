package francisco.simon.feature.income.ui.screens.history

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.history.BaseHistoryViewModel
import francisco.simon.feature.income.domain.GetAccountUseCase
import francisco.simon.feature.income.domain.GetIncomeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for Income history screen, states in base view model
 * @param getIncomeUseCase
 * @param getAccountUseCase
 * @author Simon Francisco
 */
internal class IncomeHistoryScreenViewModel @Inject constructor(
    private val getIncomeUseCase: GetIncomeUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : BaseHistoryViewModel() {
    override suspend fun getAccount(): Result<Account, Error> {
        return getAccountUseCase()
    }

    override suspend fun getTransactions(
        account: Account,
        startDate: String,
        endDate: String
    ): Flow<Result<List<Transaction>, Error>> {
        val transactionModel = TransactionModel(
            accountId = account.accountId,
            startDate = startDate,
            endDate = endDate
        )
        return getIncomeUseCase(transactionModel)
    }
}