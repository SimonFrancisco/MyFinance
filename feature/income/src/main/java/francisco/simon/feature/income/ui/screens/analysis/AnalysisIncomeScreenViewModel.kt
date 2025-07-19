package francisco.simon.feature.income.ui.screens.analysis

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.model.CategoryStatsModel
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.analyis.BaseAnalysisViewModel
import francisco.simon.feature.income.domain.GetAccountUseCase
import francisco.simon.feature.income.domain.GetIncomeAnalysisUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for Income analysis screen, state in base view model
 * @param getIncomeAnalysisUseCase
 * @param getAccountUseCase
 * @author Simon Francisco
 */
internal class AnalysisIncomeScreenViewModel @Inject constructor(
    private val getIncomeAnalysisUseCase: GetIncomeAnalysisUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : BaseAnalysisViewModel() {

    override suspend fun getAccount(): Result<Account, Error> {
        return getAccountUseCase()
    }

    override suspend fun getTransactions(
        account: Account,
        startDate: String,
        endDate: String
    ): Flow<Result<List<CategoryStatsModel>, Error>> {
        val transactionModel = TransactionModel(
            accountId = account.accountId,
            startDate = startDate,
            endDate = endDate
        )
        return getIncomeAnalysisUseCase(transactionModel)
    }
}