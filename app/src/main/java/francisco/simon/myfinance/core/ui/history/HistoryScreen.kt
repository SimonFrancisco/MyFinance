package francisco.simon.myfinance.core.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.myfinance.core.components.CustomDatePicker
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.RetryCall
import francisco.simon.myfinance.core.mapper.toApiDate
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun HistoryScreen(
    viewModel: BaseHistoryViewModel,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    var startDate by rememberSaveable {
        mutableStateOf(LocalDate.now().withDayOfMonth(1))
    }
    var endDate by rememberSaveable {
        mutableStateOf(LocalDate.now())
    }
    LaunchedEffect(Unit) {
        viewModel.loadTransactions(
            startDate = startDate.toApiDate(),
            endDate = endDate.toApiDate()
        )
    }
    val showStartPicker = remember { mutableStateOf(false) }
    val showEndPicker = remember { mutableStateOf(false) }

    if (showStartPicker.value) {
        startDate = startDatePicker(startDate, endDate, viewModel, showStartPicker)
    }
    if (showEndPicker.value) {
        endDate = endDatePicker(endDate, startDate, viewModel, showEndPicker)
    }
    HistoryScreenContent(
        state,
        viewModel,
        startDate,
        endDate,
        showStartPicker,
        showEndPicker
    )
}

@Composable
private fun HistoryScreenContent(
    state: State<HistoryScreenState>,
    viewModel: BaseHistoryViewModel,
    startDate: LocalDate,
    endDate: LocalDate,
    showStartPicker: MutableState<Boolean>,
    showEndPicker: MutableState<Boolean>
) {
    when (val currentState = state.value) {
        is HistoryScreenState.Error -> {
            RetryHistoryButton(currentState, viewModel, startDate, endDate)
        }
        is HistoryScreenState.Loading -> {
            FullScreenLoading()
        }
        is HistoryScreenState.Success -> {
            HistoryScreenList(
                currentState.transactions,
                startDate,
                showStartPicker,
                endDate,
                showEndPicker
            )
        }
    }
}

@Composable
private fun RetryHistoryButton(
    currentState: HistoryScreenState.Error,
    viewModel: BaseHistoryViewModel,
    startDate: LocalDate,
    endDate: LocalDate
) {
    RetryCall(
        errorRes = currentState.errorMessageRes,
        onClick = {
            viewModel.loadTransactions(
                startDate = startDate.toApiDate(),
                endDate = endDate.toApiDate()
            )
        },
    )
}

@Composable
private fun endDatePicker(
    endDate: LocalDate,
    startDate: LocalDate,
    viewModel: BaseHistoryViewModel,
    showEndPicker: MutableState<Boolean>
): LocalDate {
    var newEndDate = endDate
    CustomDatePicker(
        selectedDate = Date.from(newEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        onDateSelected = { date ->
            val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            if (!selected.isBefore(startDate) && !selected.isAfter(LocalDate.now())) {
                newEndDate = selected
                viewModel.loadTransactions(
                    startDate = startDate.toApiDate(),
                    endDate = selected.toApiDate()
                )
            }
        },
        onDismiss = { showEndPicker.value = false },
        minDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        maxDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
    return newEndDate
}

@Composable
private fun startDatePicker(
    startDate: LocalDate,
    endDate: LocalDate,
    viewModel: BaseHistoryViewModel,
    showStartPicker: MutableState<Boolean>
): LocalDate {
    var newStartDate = startDate
    CustomDatePicker(
        selectedDate = Date.from(newStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        onDateSelected = { date ->
            val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            if (!selected.isAfter(endDate)) {
                newStartDate = selected
                viewModel.loadTransactions(
                    startDate = selected.toApiDate(),
                    endDate = endDate.toApiDate()
                )
            }
        },
        onDismiss = { showStartPicker.value = false },
        maxDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
    return newStartDate
}