package francisco.simon.core.ui.transactions.editTransaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomDatePicker
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.components.CustomTimePicker
import francisco.simon.core.ui.transactions.editTransaction.EditTransactionBaseViewModel.EditTransaction
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.core.ui.utils.toDate
import francisco.simon.core.ui.utils.toTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date

@Composable
internal fun ShowTimePicker(
    showTimePicker: MutableState<Boolean>,
    initialTime: LocalTime,
    viewModel: EditTransactionBaseViewModel
) {
    if (showTimePicker.value) {
        CustomTimePicker(
            initialTime = initialTime,
            onTimeSelected = { time ->
                val currentDateTime = viewModel.transactionModel.value.transactionDate
                viewModel.transactionModel.value = viewModel.transactionModel.value.copy(
                    transactionDate = LocalDateTime.of(currentDateTime.toLocalDate(), time)
                )
            },
            onDismiss = {
                showTimePicker.value = false
            }
        )
    }
}

@Composable
internal fun ShowDatePicker(
    showDatePicker: MutableState<Boolean>,
    initialDate: LocalDate,
    viewModel: EditTransactionBaseViewModel
) {
    if (showDatePicker.value) {
        CustomDatePicker(
            selectedDate = Date.from(
                initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            ),
            onDateSelected = { date ->
                val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val currentDateTime = viewModel.transactionModel.value.transactionDate
                viewModel.transactionModel.value = viewModel.transactionModel.value.copy(
                    transactionDate = LocalDateTime.of(selected, currentDateTime.toLocalTime())
                )
            },
            onDismiss = { showDatePicker.value = false },
            maxDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
    }
}


@Composable
internal fun CommentInfo(transactionModel: MutableState<EditTransaction>) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp),
        headlineContent = {

            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (transactionModel.value.comment.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.comment),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    value = transactionModel.value.comment ?: "",
                    onValueChange = { newValue ->
                        transactionModel.value = transactionModel.value.copy(comment = newValue)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Start
                    )
                )
            }
        },
    )
}

@Composable
internal fun TimeInfo(
    showTimePicker: MutableState<Boolean>,
    transactionModel: MutableState<EditTransaction>
) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable {
                showTimePicker.value = true
            },
        headlineContent = {
            Text(
                text = stringResource(R.string.time),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = transactionModel.value.transactionDate.toTime(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}

@Composable
internal fun DateInfo(
    showDatePicker: MutableState<Boolean>,
    transactionModel: MutableState<EditTransaction>
) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable {
                showDatePicker.value = true
            },
        headlineContent = {
            Text(
                text = stringResource(R.string.date),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = transactionModel.value.transactionDate.toDate(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}

@Composable
internal fun AmountInfo(transactionModel: MutableState<EditTransaction>) {
    CustomListItem(
        modifier = Modifier.height(
            70.dp
        ),
        headlineContent = {
            Text(
                text = stringResource(R.string.sum),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Box(
                modifier = Modifier
                    .width(100.dp)
            ) {
                BasicTextField(
                    maxLines = 1,
                    value = transactionModel.value.amount ?: "",
                    onValueChange = { newValue ->
                        transactionModel.value = transactionModel.value.copy(amount = newValue)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.End
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = transactionModel.value.account?.currency?.toCurrencySymbol() ?: "",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )

        }
    )
}

@Composable
internal fun CategoryInfo(
    showSheet: MutableState<Boolean>,
    transactionModel: MutableState<EditTransaction>
) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable {
                showSheet.value = true
            },
        headlineContent = {
            Text(
                text = stringResource(R.string.category),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = transactionModel.value.category?.name ?: "",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}

@Composable
internal fun AccountInfo(transactionModel: MutableState<EditTransaction>) {
    CustomListItem(
        modifier = Modifier.height(70.dp),
        headlineContent = {
            Text(
                text = stringResource(R.string.account),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = transactionModel.value.account?.name ?: "",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}