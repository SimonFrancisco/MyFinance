package francisco.simon.feature.income.ui.screens.edit_income

import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.transactions.editTransaction.EditTransactionBaseViewModel
import francisco.simon.feature.income.di.TransactionIdQualifier
import francisco.simon.feature.income.domain.DeleteIncomeUseCase
import francisco.simon.feature.income.domain.EditIncomeUseCase
import francisco.simon.feature.income.domain.GetCategoriesUseCase
import francisco.simon.feature.income.domain.GetTransactionByIdUseCase
import javax.inject.Inject

internal class EditIncomeScreenViewModel @Inject constructor(
    @TransactionIdQualifier private val transactionId: Int,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteTransactionUseCase: DeleteIncomeUseCase,
    private val editTransactionUseCase: EditIncomeUseCase
) : EditTransactionBaseViewModel() {

    override suspend fun getTransaction(): Result<Transaction, Error> {
        return getTransactionByIdUseCase(transactionId)
    }

    override suspend fun getCategories(): Result<List<Category>, Error> {
        return getCategoriesUseCase()
    }

    override suspend fun editTransaction(editTransactionModel: EditTransactionModel): Result<Transaction, Error> {
        return editTransactionUseCase(editTransactionModel)
    }

    override suspend fun deleteTransaction(): EmptyResult<Error> {
        return deleteTransactionUseCase(transactionId)
    }
}