package francisco.simon.feature.income.ui.screens.add_income

import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.TransactionResponse
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.transactions.addTransaction.AddTransactionBaseViewModel
import francisco.simon.feature.income.domain.AddIncomeUseCase
import francisco.simon.feature.income.domain.GetAccountUseCase
import francisco.simon.feature.income.domain.GetCategoriesUseCase
import javax.inject.Inject

internal class AddIncomeScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addIncomeUseCase: AddIncomeUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : AddTransactionBaseViewModel() {

    override suspend fun getAccount(): Result<Account, Error> {
        return getAccountUseCase()
    }

    override suspend fun getCategories(): Result<List<Category>, Error> {
        return getCategoriesUseCase()
    }

    override suspend fun addTransaction(addTransactionModel: AddTransactionModel): Result<TransactionResponse, Error> {
        return addIncomeUseCase(addTransactionModel)
    }
}