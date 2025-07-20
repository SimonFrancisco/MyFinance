package francisco.simon.core.data.network.repositories

import francisco.simon.core.data.local.account.db.AccountDao
import francisco.simon.core.data.local.category.db.CategoriesDao
import francisco.simon.core.data.local.transactions.db.TransactionDao
import francisco.simon.core.data.local.transactions.mappers.toAccountTransaction
import francisco.simon.core.data.local.transactions.mappers.toAddDB
import francisco.simon.core.data.local.transactions.mappers.toCategoryTransaction
import francisco.simon.core.data.local.transactions.mappers.toEndTransactionDate
import francisco.simon.core.data.local.transactions.mappers.toListTransaction
import francisco.simon.core.data.local.transactions.mappers.toStartTransactionDate
import francisco.simon.core.data.local.transactions.mappers.toTransaction
import francisco.simon.core.data.local.transactions.mappers.toTransactionResponseModel
import francisco.simon.core.data.local.transactions.model.TransactionDbModel
import francisco.simon.core.data.network.api.ApiClient
import francisco.simon.core.data.network.api.ApiService
import francisco.simon.core.data.network.dto.transactions.TransactionDto
import francisco.simon.core.data.network.dto.transactions.TransactionResponseDto
import francisco.simon.core.data.network.mappers.toAddTransactionDto
import francisco.simon.core.data.network.mappers.toEditTransactionDtoModel
import francisco.simon.core.data.network.mappers.toTransaction
import francisco.simon.core.data.network.mappers.toTransactionResponse
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.model.TransactionModel
import francisco.simon.core.domain.model.TransactionResponseModel
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

/**
 * Implementation of Transaction repository, operations happen in Dispatcher IO
 * Network-first, fallback to DB
 * @param apiService
 * @param apiClient
 * @author Simon Francisco
 */
class TransactionRepositoryImpl(
    private val apiService: ApiService,
    private val apiClient: ApiClient,
    private val categoriesDao: CategoriesDao,
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun getTransactions(
        transactionModel: TransactionModel
    ): Flow<Result<List<Transaction>, Error>> {
        synchronize()
        return flow {
            try {
                when (val apiResult = apiClient.safeApiCall {
                    apiService.getTransactions(
                        accountId = transactionModel.accountId,
                        startDate = transactionModel.startDate,
                        endDate = transactionModel.endDate
                    )
                }) {
                    is Result.Error<NetworkError> -> {
                        val localTransactions = transactionDao.getTransactions(
                            accountId = transactionModel.accountId,
                            startDate = transactionModel.startDate.toStartTransactionDate(),
                            endDate = transactionModel.endDate.toEndTransactionDate()
                        ).sortedByDescending { it.transactionDate }
                        emit(Result.Success(localTransactions.toListTransaction()))
                    }

                    is Result.Success<List<TransactionDto>> -> {
                        apiResult.data.map {
                            transactionDao.insertTransaction(it.toAddDB())
                        }
                        val transactions = apiResult.data.sortedByDescending { it.transactionDate }
                            .map { transactionDto ->
                                transactionDto.toTransaction()
                            }
                        emit(Result.Success(transactions))
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                emit(Result.Error(NetworkError.UNKNOWN))
            }
        }.flowOn(Dispatchers.IO)

    }
    override suspend fun getTransactionById(transactionId: Int): Result<Transaction, Error> {
        return withContext(Dispatchers.IO) {
            try {
                when (val apiResult = apiClient.safeApiCall {
                    apiService.getTransactionById(transactionId)
                }) {
                    is Result.Error<NetworkError> -> {
                        val localTransaction = transactionDao.getTransactionById(transactionId)
                        Result.Success(localTransaction.toTransaction())
                    }

                    is Result.Success<TransactionDto> -> {
                        Result.Success(apiResult.data.toTransaction())
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }

    // TODO propagate changes to amount in account
    override suspend fun addTransaction(transactionModel: AddTransactionModel): Result<TransactionResponseModel, Error> {
        return withContext(Dispatchers.IO) {
            try {
                val account = accountDao.getAccount()
                val category = categoriesDao.getCategoryById(transactionModel.categoryId)
                when (val apiResult = apiClient.safeApiCall {
                    apiService.addTransaction(
                        transactionModel.toAddTransactionDto()
                    )
                }) {
                    is Result.Error<NetworkError> -> {
                        val transactionDbModel = TransactionDbModel(
                            accountTransaction = account.toAccountTransaction(),
                            categoryTransaction = category.toCategoryTransaction(),
                            amount = transactionModel.amount,
                            transactionDate = transactionModel.transactionDate,
                            comment = transactionModel.comment,
                            createdAt = Instant.now().toString(),
                            updatedAt = Instant.now().toString(),
                            isAdded = false,
                            isEdited = true
                        )
                        transactionDao.insertTransaction(
                            transactionDbModel = transactionDbModel
                        )
                        Result.Success(transactionDbModel.toTransactionResponseModel())
                    }

                    is Result.Success<TransactionResponseDto> -> {
                        val transactionResponseDto = apiResult.data
                        val transactionDbModel = TransactionDbModel(
                            transactionId = transactionResponseDto.id,
                            accountTransaction = account.toAccountTransaction(),
                            categoryTransaction = category.toCategoryTransaction(),
                            amount = transactionResponseDto.amount,
                            transactionDate = transactionResponseDto.transactionDate,
                            comment = transactionResponseDto.comment,
                            createdAt = transactionResponseDto.createdAt,
                            updatedAt = transactionResponseDto.updatedAt,
                            isAdded = true,
                            isEdited = true
                        )
                        transactionDao.insertTransaction(
                            transactionDbModel
                        )
                        Result.Success(transactionResponseDto.toTransactionResponse())
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }

    // TODO propagate changes to amount in account
    override suspend fun editTransaction(transactionModel: EditTransactionModel): Result<Transaction, Error> {
        return withContext(Dispatchers.IO) {
            try {
                val localTransaction =
                    transactionDao.getTransactionById(transactionId = transactionModel.transactionId)
                when (val apiResult = apiClient.safeApiCall {
                    apiService.editTransaction(
                        transactionId = transactionModel.transactionId,
                        editTransactionDto = transactionModel.toEditTransactionDtoModel()
                    )
                }) {
                    is Result.Error<NetworkError> -> {
                        transactionDao.updateTransaction(
                            localTransaction.copy(
                                categoryTransaction = localTransaction.categoryTransaction.copy(
                                    categoryId = transactionModel.categoryId
                                ),
                                comment = transactionModel.comment,
                                transactionDate = transactionModel.transactionDate,
                                amount = transactionModel.amount,
                                isEdited = false
                            )
                        )
                        Result.Success(
                            transactionDao.getTransactionById(transactionModel.transactionId)
                                .toTransaction()
                        )
                    }

                    is Result.Success<TransactionDto> -> {
                        val transaction = apiResult.data
                        transactionDao.updateTransaction(
                            localTransaction.copy(
                                categoryTransaction = localTransaction.categoryTransaction.copy(
                                    categoryId = transaction.category.id
                                ),
                                comment = transaction.comment,
                                transactionDate = transaction.transactionDate,
                                amount = transaction.amount,
                                isEdited = true
                            )
                        )
                        Result.Success(transaction.toTransaction())
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }


    override suspend fun deleteTransaction(transactionId: Int): EmptyResult<Error> {
        return withContext(Dispatchers.IO) {
            try {
                when (val apiResult = apiClient.safeApiCall {
                    apiService.deleteTransaction(transactionId)
                }) {
                    is Result.Error<NetworkError> -> {
                        if (apiResult.error == NetworkError.NULL) {
                            transactionDao.deleteTransactionById(transactionId)
                            Result.Error(apiResult.error)
                        } else {
                            val localTransaction = transactionDao.getTransactionById(transactionId)
                            transactionDao.updateTransaction(localTransaction.copy(isDeleted = true))
                            Result.Error(NetworkError.NULL)
                        }
                    }

                    is Result.Success<Unit> -> {
                        Result.Success(Unit)
                    }
                }
            } catch (e: Exception) {
                // TODO change to DB error
                Result.Error(NetworkError.UNKNOWN)
            }

        }
    }

    override suspend fun synchronize() {
        withContext(Dispatchers.IO) {
            launch {
                val notAddedTransactions = transactionDao.getNotAddedTransactions()
                if (notAddedTransactions.isEmpty()) {
                    return@launch
                }
                notAddedTransactions.forEach { transactionDbModel ->
                    val addTransactionModel = AddTransactionModel(
                        accountId = transactionDbModel.accountTransaction.accountId,
                        categoryId = transactionDbModel.categoryTransaction.categoryId,
                        amount = transactionDbModel.amount,
                        transactionDate = transactionDbModel.transactionDate,
                        comment = transactionDbModel.comment
                    )
                    addTransaction(addTransactionModel).onSuccess {
                        transactionDao.deleteTransactionById(transactionDbModel.transactionId)
                    }
                }
            }
            launch {
                val notEditedTransactions = transactionDao.getNotEditedTransaction()

                if (notEditedTransactions.isEmpty()) {
                    return@launch
                }
                notEditedTransactions.forEach { transactionDbModel ->
                    val editTransactionModel = EditTransactionModel(
                        transactionId = transactionDbModel.transactionId,
                        accountId = transactionDbModel.accountTransaction.accountId,
                        categoryId = transactionDbModel.categoryTransaction.categoryId,
                        comment = transactionDbModel.comment,
                        amount = transactionDbModel.amount,
                        transactionDate = transactionDbModel.transactionDate
                    )
                    if (!transactionDbModel.isDeleted) {
                        editTransaction(editTransactionModel)
                    }
                }
            }
            launch {
                val notDeletedTransactions = transactionDao.getNotDeletedTransaction()
                if (notDeletedTransactions.isEmpty()) {
                    return@launch
                }
                notDeletedTransactions.forEach {
                    deleteTransaction(it.transactionId)
                }
            }
        }

    }
}