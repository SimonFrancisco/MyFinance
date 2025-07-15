package francisco.simon.core.ui.transactions.editTransaction

import androidx.annotation.StringRes

sealed class EditTransactionState {
    data object Loading : EditTransactionState()
    data object Success : EditTransactionState()
    data class Error(@StringRes val errorMessageRes: Int) : EditTransactionState()
}