package francisco.simon.myfinance.ui.features.expense.screens.history

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.history.BaseHistoryViewModel
import francisco.simon.myfinance.domain.usecase.GetAccountUseCase
import francisco.simon.myfinance.domain.usecase.GetExpenseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for Expense history screen, state in base view model
 * @param getExpenseUseCase
 * @param getAccountUseCase
 * @author Simon Francisco
 */
class ExpensesHistoryScreenViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
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
            accountId = account.id,
            startDate = startDate,
            endDate = endDate
        )
        return getExpenseUseCase(transactionModel)
    }
}