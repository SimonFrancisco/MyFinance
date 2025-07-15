package francisco.simon.core.ui.transactions.addTransaction

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.TransactionResponse
import francisco.simon.core.domain.utils.Error
import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.domain.utils.Result
import francisco.simon.core.domain.utils.onError
import francisco.simon.core.domain.utils.onSuccess
import francisco.simon.core.ui.utils.toStringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

abstract class AddTransactionBaseViewModel : ViewModel() {

    private val _state = MutableStateFlow<AddTransactionState>(AddTransactionState.Loading)
    val state: StateFlow<AddTransactionState> = _state

    val transactionModel: MutableState<AddTransaction> = mutableStateOf(AddTransaction())
    val categoriesList: MutableState<List<Category>> = mutableStateOf(emptyList())

    private val _dataMissingError = Channel<Unit>()
    val dataMissingError: ReceiveChannel<Unit> = _dataMissingError

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    protected abstract suspend fun getAccount(): Result<Account, Error>

    protected abstract suspend fun getCategories(): Result<List<Category>, Error>

    protected abstract suspend fun addTransaction(
        addTransactionModel: AddTransactionModel
    ): Result<TransactionResponse, Error>


    fun onLoadInitial() {
        updateLoading()
        viewModelScope.launch {
            getAccount().onSuccess { account: Account ->
                transactionModel.value = transactionModel.value.copy(account = account)
                getCategories().onSuccess { categories ->
                    categoriesList.value = categories
                    _state.update {
                        AddTransactionState.Success
                    }
                }.onError { error ->
                    updateError(error)
                }
            }.onError { error ->
                updateError(error)
            }
        }
    }

    fun onAddTransaction() {
        viewModelScope.launch {
            if (!validateTransactionModel()) {
                _dataMissingError.send(Unit)
                return@launch
            }
            updateLoading()
            with(transactionModel.value) {
                val addTransactionModel = AddTransactionModel(
                    accountId = account?.id ?: 0,
                    categoryId = category?.id ?: 0,
                    amount = amount?.trim() ?: "",
                    transactionDate = transactionDate.toTransactionModelTime(),
                    comment = comment?.trim()
                )
                addTransaction(
                    addTransactionModel
                ).onSuccess { _ ->
                    _exitChannel.send(Unit)
                }.onError { error ->
                    updateError(error)
                }
            }

        }
    }


    fun retry() {
        if (transactionModel.value.account != null && categoriesList.value.isNotEmpty()) {
            onAddTransaction()
        } else {
            onLoadInitial()
        }
    }


    private fun updateLoading() {
        _state.update {
            AddTransactionState.Loading
        }
    }

    private fun updateError(error: Error) {
        val errorRes = (error as NetworkError).toStringRes()
        _state.update {
            AddTransactionState.Error(errorMessageRes = errorRes)
        }
    }

    private fun validateTransactionModel(): Boolean {
        return with(transactionModel.value) {
            account != null && category != null && amount != null
        }
    }

    private fun LocalDateTime.toTransactionModelTime(): String {
        return this.atZone(ZoneId.systemDefault())
            .toInstant()
            .toString()
    }


    data class AddTransaction(
        val account: Account? = null,
        val category: Category? = null,
        val amount: String? = null,
        val transactionDate: LocalDateTime = LocalDateTime.now(),
        val comment: String? = null
    )


}

