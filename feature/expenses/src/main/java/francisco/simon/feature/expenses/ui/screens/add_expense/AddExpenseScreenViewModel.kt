package francisco.simon.feature.expenses.ui.screens.add_expense

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.TransactionResponse
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.transactions.addTransaction.AddTransactionBaseViewModel
import francisco.simon.feature.expenses.domain.AddExpenseUseCase
import francisco.simon.feature.expenses.domain.GetAccountUseCase
import francisco.simon.feature.expenses.domain.GetCategoriesUseCase
import javax.inject.Inject

internal class AddExpenseScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : AddTransactionBaseViewModel() {

    override suspend fun getAccount(): Result<Account, Error> {
        return getAccountUseCase()
    }

    override suspend fun getCategories(): Result<List<Category>, Error> {
        return getCategoriesUseCase()
    }

    override suspend fun addTransaction(addTransactionModel: AddTransactionModel): Result<TransactionResponse, Error> {
        return addExpenseUseCase(addTransactionModel)
    }
}