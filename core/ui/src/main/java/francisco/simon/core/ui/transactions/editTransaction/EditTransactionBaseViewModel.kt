package francisco.simon.core.ui.transactions.editTransaction

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.utils.EmptyResult
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.onError
import francisco.simon.core.domain.utils.onSuccess
import francisco.simon.core.ui.utils.toLocalDateTime
import francisco.simon.core.ui.utils.toStringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

abstract class EditTransactionBaseViewModel : ViewModel() {

    private val _state = MutableStateFlow<EditTransactionState>(EditTransactionState.Loading)
    val state: StateFlow<EditTransactionState> = _state

    val transactionModel: MutableState<EditTransaction> = mutableStateOf(EditTransaction())
    val categoriesList: MutableState<List<Category>> = mutableStateOf(emptyList())

    private val _dataMissingError = Channel<Unit>()
    val dataMissingError: ReceiveChannel<Unit> = _dataMissingError

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    protected abstract suspend fun getTransaction(): Result<Transaction, Error>

    protected abstract suspend fun getCategories(): Result<List<Category>, Error>

    protected abstract suspend fun editTransaction(
        editTransactionModel: EditTransactionModel
    ): Result<Transaction, Error>

    protected abstract suspend fun deleteTransaction(): EmptyResult<Error>


    fun onLoadInitial() {
        updateLoading()
        viewModelScope.launch {
            getTransaction().onSuccess { transaction ->
                transactionModel.value = transactionModel.value.copy(account = transaction.account)
                transactionModel.value =
                    transactionModel.value.copy(category = transaction.category)
                transactionModel.value = transactionModel.value.copy(transactionId = transaction.id)
                transactionModel.value =
                    transactionModel.value.copy(transactionDate = transaction.transactionDate.toLocalDateTime())
                transactionModel.value = transactionModel.value.copy(amount = transaction.amount)
                transactionModel.value = transactionModel.value.copy(comment = transaction.comment)
                getCategories().onSuccess { categories ->
                    categoriesList.value = categories
                    _state.update {
                        EditTransactionState.Success
                    }
                }.onError { error ->
                    updateError(error)
                }
            }.onError { error ->
                updateError(error)
            }
        }
    }

    fun onEditTransaction() {
        viewModelScope.launch {
            if (!validateTransactionModel()) {
                _dataMissingError.send(Unit)
                return@launch
            }
            updateLoading()
            with(transactionModel.value) {
                val editTransactionModel = EditTransactionModel(
                    transactionId = transactionId ?: 0,
                    accountId = account?.id ?: 0,
                    categoryId = category?.id ?: 0,
                    amount = amount?.trim() ?: "",
                    transactionDate = transactionDate.toTransactionModelTime(),
                    comment = comment?.trim()
                )
                editTransaction(
                    editTransactionModel
                ).onSuccess { _ ->
                    _exitChannel.send(Unit)
                }.onError { error ->
                    updateError(error)
                }
            }

        }
    }

    fun onDeleteTransaction() {
        updateLoading()
        viewModelScope.launch {
            deleteTransaction().onSuccess {
                _exitChannel.send(Unit)

            }.onError { error ->
                if (error is NetworkError) {
                    if (error == NetworkError.NULL) {
                        _exitChannel.send(Unit)
                        return@launch
                    }
                }
                updateError(error)
            }
        }
    }


    fun retry() {
        if (transactionModel.value.transactionId != null && categoriesList.value.isNotEmpty()) {
            onEditTransaction()
        } else {
            onLoadInitial()
        }
    }


    private fun updateLoading() {
        _state.update {
            EditTransactionState.Loading
        }
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            EditTransactionState.Error(errorMessageRes = errorRes)
        }
    }

    private fun validateTransactionModel(): Boolean {
        return with(transactionModel.value) {
            transactionId != null && account != null && category != null && !amount?.trim().isNullOrEmpty()
        }
    }

    private fun LocalDateTime.toTransactionModelTime(): String {
        return this.atZone(ZoneId.systemDefault())
            .toInstant()
            .toString()
    }

    data class EditTransaction(
        val transactionId: Int? = null,
        val account: Account? = null,
        val category: Category? = null,
        val amount: String? = null,
        val transactionDate: LocalDateTime = LocalDateTime.now(),
        val comment: String? = null
    )


}

