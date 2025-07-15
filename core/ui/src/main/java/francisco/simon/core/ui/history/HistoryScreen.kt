package francisco.simon.core.ui.history

import androidx.annotation.StringRes
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
import francisco.simon.core.ui.components.CustomDatePicker
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.RetryCall
import francisco.simon.core.ui.utils.toApiDate
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

/**
 * History screen, it takes a base view model.
 *
 * @author Simon Francisco
 */
@Composable
fun HistoryScreen(
    viewModel: BaseHistoryViewModel,
    onTransactionClicked: (Int) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val startDate = rememberSaveable {
        mutableStateOf(LocalDate.now().withDayOfMonth(1))
    }
    val endDate = rememberSaveable {
        mutableStateOf(LocalDate.now())
    }
    LaunchedEffect(Unit) {
        viewModel.loadTransactions(
            startDate = startDate.value.toApiDate(),
            endDate = endDate.value.toApiDate()
        )
    }
    val showStartPicker = remember { mutableStateOf(false) }
    val showEndPicker = remember { mutableStateOf(false) }

    if (showStartPicker.value) {
        StartDatePicker(startDate, endDate, viewModel, showStartPicker)
    }
    if (showEndPicker.value) {
        EndDatePicker(endDate, startDate, viewModel, showEndPicker)
    }
    HistoryScreenContent(
        state,
        viewModel,
        startDate,
        endDate,
        showStartPicker,
        showEndPicker,
        onTransactionClicked
    )
}

/**
 * Separate History screen content to avoid extra recompositions
 *
 * @author Simon Francisco
 */
@Composable
private fun HistoryScreenContent(
    state: State<HistoryScreenState>,
    viewModel: BaseHistoryViewModel,
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>,
    showStartPicker: MutableState<Boolean>,
    showEndPicker: MutableState<Boolean>,
    onTransactionClicked: (Int) -> Unit

) {
    Column(modifier = Modifier.fillMaxSize()) {
        StartInfo(startDate.value, showStartPicker)
        HorizontalDivider()
        EndInfo(endDate.value, showEndPicker)
        HorizontalDivider()
        when (val currentState = state.value) {
            is HistoryScreenState.Error -> {
                RetryHistoryButton(
                    currentState.errorMessageRes,
                    viewModel,
                    startDate.value,
                    endDate.value
                )
            }

            is HistoryScreenState.Loading -> {
                FullScreenLoading()
            }

            is HistoryScreenState.Success -> {
                HistoryScreenList(
                    currentState.transactions,
                    onTransactionClicked
                )
            }
        }
    }

}

@Composable
private fun RetryHistoryButton(
    @StringRes errorMessageRes: Int,
    viewModel: BaseHistoryViewModel,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    RetryCall(
        errorRes = errorMessageRes,
        onClick = {
            viewModel.loadTransactions(
                startDate = startDate.toApiDate(),
                endDate = endDate.toApiDate()
            )
        },
    )
}

/**
 * Date picker for choosing end date
 *
 * @author Simon Francisco
 */
@Composable
private fun EndDatePicker(
    endDate: MutableState<LocalDate>,
    startDate: MutableState<LocalDate>,
    viewModel: BaseHistoryViewModel,
    showEndPicker: MutableState<Boolean>,
) {
    CustomDatePicker(
        selectedDate = Date.from(endDate.value.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        onDateSelected = { date ->
            val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            if (!selected.isBefore(startDate.value) && !selected.isAfter(LocalDate.now())) {
                endDate.value = selected
                viewModel.loadTransactions(
                    startDate = startDate.value.toApiDate(),
                    endDate = selected.toApiDate()
                )
            }
        },
        onDismiss = { showEndPicker.value = false },
        minDate = Date.from(startDate.value.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        maxDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    )

}

/**
 * Date picker for choosing start date
 *
 * @author Simon Francisco
 */
@Composable
private fun StartDatePicker(
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>,
    viewModel: BaseHistoryViewModel,
    showStartPicker: MutableState<Boolean>,
) {
    CustomDatePicker(
        selectedDate = Date.from(startDate.value.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        onDateSelected = { date ->
            val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            if (!selected.isAfter(endDate.value)) {
                startDate.value = selected
                viewModel.loadTransactions(
                    startDate = selected.toApiDate(),
                    endDate = endDate.value.toApiDate()
                )
            }
        },
        onDismiss = { showStartPicker.value = false },
        maxDate = Date.from(endDate.value.atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
}