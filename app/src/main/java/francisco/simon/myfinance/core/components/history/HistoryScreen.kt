package francisco.simon.myfinance.core.components.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.core.mapper.toLocalDateTime
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.ui.theme.Green
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@Composable
fun HistoryScreen(transactions: List<Transaction>) {

    Column(modifier = Modifier.fillMaxSize()) {
        val sum = transactions.sumOf {
            it.amount.toBigDecimal()
        }
        var showDatePicker by remember {
            mutableStateOf(false)
        }
        var localDate by remember {
            mutableStateOf(LocalDate.now())
        }

        Calendar(showDatePicker) {
            localDate = it
        }
        CustomListItem(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    showDatePicker = true
                },
            headlineContent = {
                Text(
                    text = stringResource(R.string.beginning),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
                Text(
                    text = "${localDate.month.name} ${localDate.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    showDatePicker = true

                },
            headlineContent = {
                Text(
                    text = stringResource(R.string.end),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
                Text(
                    text = "${localDate.month.name} ${localDate.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            headlineContent = {
                Text(
                    text = stringResource(R.string.sum),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
                Text(
                    text = "$sum ${transactions.first().account.currency.toCurrencySymbol()}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        )
        LazyColumn {
            items(transactions, key = { it.id }) { transaction ->
                CustomListItem(
                    modifier = Modifier
                        .height(70.dp)
                        .clickable {

                        },
                    headlineContent = {
                        Text(
                            text = transaction.category.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        if (!transaction.comment.isNullOrEmpty()) {
                            Text(
                                text = transaction.comment,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }

                    },
                    leadingContent = {
                        Box(
                            Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(transaction.category.emoji)
                        }
                        Spacer(Modifier.width(16.dp))
                    },
                    trailingContent = {
                        Column {
                            val localDateTime = transaction.updatedAt.toLocalDateTime()
                            Text(
                                text = "${transaction.amount} ${transaction.account.currency.toCurrencySymbol()}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = "${localDateTime.hour}:${localDateTime.minute}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_head),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(showDatePicker: Boolean, onDateClicked: (LocalDate) -> Unit) {
    var showDatePickerInternal by remember {
        mutableStateOf(showDatePicker)
    }
    val datePickerState = rememberDatePickerState()
    if (showDatePickerInternal) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerInternal = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate =
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        onDateClicked(selectedDate)
                    }
                    showDatePickerInternal = false
                }) {
                    Text(
                        text = stringResource(android.R.string.ok),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerInternal = false }) {
                    Text(
                        text = stringResource(android.R.string.cancel),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = DatePickerDefaults.colors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        ) {
            DatePicker(
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedDayContainerColor = Green,
                    selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                    todayContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                state = datePickerState,
                title = null,
                headline = null,
                showModeToggle = false
            )
        }
    }
}