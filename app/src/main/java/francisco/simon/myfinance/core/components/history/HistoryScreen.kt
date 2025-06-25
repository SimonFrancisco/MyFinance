package francisco.simon.myfinance.core.components.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomDatePicker
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.RetryCall
import francisco.simon.myfinance.core.mapper.toApiDate
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.core.mapper.toDateWritten
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
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
        CustomDatePicker(
            selectedDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            onDateSelected = { date ->
                val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                if (!selected.isAfter(endDate)) {
                    startDate = selected
                    viewModel.loadTransactions(
                        startDate = selected.toApiDate(),
                        endDate = endDate.toApiDate()
                    )
                }
            },
            onDismiss = { showStartPicker.value = false },
            maxDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
    }
    if (showEndPicker.value) {
        CustomDatePicker(
            selectedDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            onDateSelected = { date ->
                val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                if (!selected.isBefore(startDate) && !selected.isAfter(LocalDate.now())) {
                    endDate = selected
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
    }

    Box(
        modifier = modifier
    ) {
        when (val currentState = state.value) {
            is HistoryScreenState.Error -> {
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

            is HistoryScreenState.Loading -> {
                FullScreenLoading()
            }

            is HistoryScreenState.Success -> {
                val transactions = currentState.transactions
                val sum = transactions.sumOf {
                    it.amount.toBigDecimal()
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        InfoItem(
                            leadingTextResId = R.string.start,
                            trailingText = startDate.toDateWritten(),
                            onClick = {
                                showStartPicker.value = true
                            }
                        )
                        HorizontalDivider()
                    }
                    item {
                        InfoItem(
                            leadingTextResId = R.string.end,
                            trailingText = endDate.toDateWritten(),
                            onClick = {
                                showEndPicker.value = true
                            }
                        )
                        HorizontalDivider()
                    }
                    item {
                        InfoItem(
                            leadingTextResId = R.string.sum,
                            trailingText = "$sum ${transactions.firstOrNull()?.account?.currency?.toCurrencySymbol() ?: " "}",
                            onClick = {
                            }
                        )
                    }
                    items(transactions, key = { it.id }) { transaction ->
                        HistoryListItem(transaction)
                        HorizontalDivider()
                    }
                }
            }
        }
    }

}