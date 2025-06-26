@file:OptIn(ExperimentalMaterial3Api::class)

package francisco.simon.myfinance.core.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import francisco.simon.myfinance.R
import francisco.simon.myfinance.ui.theme.Green
import java.util.Calendar
import java.util.Date

@Composable
fun CustomDatePicker(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    minDate: Date? = null,
    maxDate: Date? = null
) {
    val initialDateMillis = initialDateMillis(selectedDate)

    val minDateMillis = minDateMillis(minDate)

    val maxDateMillis = maxDateMillis(maxDate)

    val datePickerState = datePickerState(initialDateMillis, minDateMillis, maxDateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            ConfirmButton(datePickerState, onDateSelected, onDismiss, textColor)
        },
        dismissButton = {
            DismissButton(onDismiss, textColor)
        },
        modifier = modifier,
        colors = DatePickerDefaults.colors(
            containerColor = containerColor,
        )
    ) {
        DatePickerSettings(datePickerState, containerColor)
    }
}

@Composable
private fun ConfirmButton(
    datePickerState: DatePickerState,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    textColor: Color
) {
    TextButton(
        onClick = {
            datePickerState.selectedDateMillis?.let { millis ->
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = millis
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                onDateSelected(calendar.time)
            }
            onDismiss()
        }
    ) {
        Text(
            text = stringResource(R.string.ok),
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Composable
private fun DismissButton(onDismiss: () -> Unit, textColor: Color) {
    TextButton(onClick = onDismiss) {
        Text(
            text = stringResource(R.string.exit),
            color = textColor
        )
    }
}

@Composable
private fun DatePickerSettings(
    datePickerState: DatePickerState,
    containerColor: Color
) {
    DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = containerColor,
            selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
            selectedDayContainerColor = Green,
            todayDateBorderColor = Green,
            todayContentColor = MaterialTheme.colorScheme.onSurface,
            selectedYearContainerColor = Green,
            selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
            currentYearContentColor = MaterialTheme.colorScheme.onSurface,
        )
    )
}

@Composable
private fun datePickerState(
    initialDateMillis: Long?,
    minDateMillis: Long?,
    maxDateMillis: Long?
): DatePickerState {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        yearRange = IntRange(2000, 2099),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val minCheck = minDateMillis?.let { utcTimeMillis >= it } ?: true
                val maxCheck = maxDateMillis?.let { utcTimeMillis <= it } ?: true
                return minCheck && maxCheck
            }
        }
    )
    return datePickerState
}

@Composable
private fun maxDateMillis(maxDate: Date?): Long? {
    val maxDateMillis = maxDate?.let { date ->
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        calendar.timeInMillis
    }
    return maxDateMillis
}

@Composable
private fun minDateMillis(minDate: Date?): Long? {
    val minDateMillis = minDate?.let { date ->
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        calendar.timeInMillis
    }
    return minDateMillis
}

@Composable
private fun initialDateMillis(selectedDate: Date?): Long? {
    val initialDateMillis = selectedDate?.let { date ->
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        calendar.timeInMillis
    }
    return initialDateMillis
}