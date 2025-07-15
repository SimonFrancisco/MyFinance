package francisco.simon.feature.expenses.ui.screens.edit_expense

import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.ui.transactions.editTransaction.EditTransactionBaseViewModel
import francisco.simon.feature.expenses.di.TransactionIdQualifier
import francisco.simon.feature.expenses.domain.DeleteExpenseUseCase
import francisco.simon.feature.expenses.domain.EditExpenseUseCase
import francisco.simon.feature.expenses.domain.GetCategoriesUseCase
import francisco.simon.feature.expenses.domain.GetTransactionByIdUseCase
import javax.inject.Inject

internal class EditExpenseScreenViewModel @Inject constructor(
    @TransactionIdQualifier private val transactionId: Int,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteTransactionUseCase: DeleteExpenseUseCase,
    private val editTransactionUseCase: EditExpenseUseCase
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