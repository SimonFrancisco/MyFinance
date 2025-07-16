package francisco.simon.core.ui.transactions.addTransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.RetryCall
import francisco.simon.core.ui.transactions.AddCategoryBottomSheet
import francisco.simon.core.ui.transactions.addTransaction.AddTransactionBaseViewModel.AddTransaction
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionBaseViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onLoadInitial()
    }
    val initialDate = rememberSaveable {
        mutableStateOf(LocalDate.now().withDayOfMonth(1))
    }
    val initialTime = rememberSaveable {
        mutableStateOf(LocalTime.now())
    }
    val showSheet = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    AddCategoryBottomSheet(
        showSheet = showSheet,
        updateModelState = viewModel.transactionModel,
        categories = viewModel.categoriesList.value
    )
    ShowDatePicker(showDatePicker, initialDate, viewModel)
    ShowTimePicker(showTimePicker, initialTime, viewModel)
    AddTransactionScreenContent(
        viewModel = viewModel,
        transactionModel = viewModel.transactionModel,
        showDatePicker = showDatePicker,
        showTimePicker = showTimePicker,
        showSheet = showSheet,
        state = state
    )

}
@Composable
private fun AddTransactionScreenContent(
    viewModel: AddTransactionBaseViewModel,
    transactionModel: MutableState<AddTransaction>,
    showDatePicker: MutableState<Boolean>,
    showSheet: MutableState<Boolean>,
    showTimePicker: MutableState<Boolean>,
    state: State<AddTransactionState>,
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
        when (val currentState = state.value) {
            is AddTransactionState.Error -> {
                RetryCall(
                    currentState.errorMessageRes
                ) {
                    viewModel.retry()
                }
            }

            AddTransactionState.Loading -> {
                FullScreenLoading()

            }

            AddTransactionState.Success -> Unit
        }

    }
}

