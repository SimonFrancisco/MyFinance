package francisco.simon.core.ui.transactions.addTransaction

import androidx.annotation.StringRes

sealed class AddTransactionState {
    data object Loading : AddTransactionState()
    data object Success : AddTransactionState()
    data class Error(@StringRes val errorMessageRes: Int) : AddTransactionState()
}