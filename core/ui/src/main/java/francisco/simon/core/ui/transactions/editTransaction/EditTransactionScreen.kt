package francisco.simon.core.ui.transactions.editTransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.RetryCall
import francisco.simon.core.ui.transactions.EditCategoryBottomSheet
import francisco.simon.core.ui.transactions.editTransaction.EditTransactionBaseViewModel.EditTransaction

@Composable
fun EditTransactionScreen(
    viewModel: EditTransactionBaseViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onLoadInitial()
    }

    val showSheet = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }

    val isEnabledButton = state.value !is EditTransactionState.Loading

    EditCategoryBottomSheet(
        showSheet = showSheet,
        updateModelState = viewModel.transactionModel,
        categories = viewModel.categoriesList.value
    )
    ShowDatePicker(
        showDatePicker,
        viewModel.transactionModel.value.transactionDate.toLocalDate(),
        viewModel
    )
    ShowTimePicker(
        showTimePicker,
        viewModel.transactionModel.value.transactionDate.toLocalTime(),
        viewModel
    )
    AddTransactionScreenContent(
        viewModel = viewModel,
        transactionModel = viewModel.transactionModel,
        showDatePicker = showDatePicker,
        showTimePicker = showTimePicker,
        showSheet = showSheet,
        isEnabledButton = isEnabledButton,
        state = state
    )

}

@Composable
private fun AddTransactionScreenContent(
    viewModel: EditTransactionBaseViewModel,
    transactionModel: MutableState<EditTransaction>,
    showDatePicker: MutableState<Boolean>,
    showSheet: MutableState<Boolean>,
    showTimePicker: MutableState<Boolean>,
    isEnabledButton: Boolean,
    state: State<EditTransactionState>,
) {

    Column(modifier = Modifier.fillMaxSize()) {
        AccountInfo(transactionModel)
        HorizontalDivider()
        CategoryInfo(showSheet, transactionModel)
        HorizontalDivider()
        AmountInfo(transactionModel)
        HorizontalDivider()
        DateInfo(showDatePicker, transactionModel)
        HorizontalDivider()
        TimeInfo(showTimePicker, transactionModel)
        HorizontalDivider()
        CommentInfo(transactionModel)
        HorizontalDivider()
        DeleteButton(
            onDeleteTransaction = viewModel::onDeleteTransaction,
            isEnabledButton = isEnabledButton
        )
        when (val currentState = state.value) {
            is EditTransactionState.Error -> {
                RetryCall(
                    currentState.errorMessageRes
                ) {
                    viewModel.retry()
                }
            }

            EditTransactionState.Loading -> {
                FullScreenLoading()
            }

            EditTransactionState.Success -> Unit
        }
    }
}

@Composable
private fun DeleteButton(
    onDeleteTransaction: () -> Unit,
    isEnabledButton: Boolean
) {
    Button(
        onClick = {
            onDeleteTransaction()
        },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.onErrorContainer,
        ),
        modifier = Modifier
            .width(380.dp)
            .padding(vertical = 10.dp, horizontal = 24.dp),
        shape = CircleShape,
        enabled = isEnabledButton
    ) {
        Text(
            text = stringResource(R.string.delete),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

