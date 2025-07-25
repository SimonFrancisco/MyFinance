@file:OptIn(ExperimentalMaterial3Api::class)

package francisco.simon.core.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import francisco.simon.core.ui.R
import java.util.Calendar
import java.util.Date

/**
 * The date picker supports day, month and year.
 * @author Simon Francisco
 */
@Composable
fun CustomDatePicker(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
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
            ConfirmButton(datePickerState, onDateSelected, onDismiss)
        },
        dismissButton = {
            DismissButton(onDismiss)
        },
        modifier = modifier,
    ) {
        DatePickerSettings(datePickerState)
    }
}

@Composable
private fun ConfirmButton(
    datePickerState: DatePickerState,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
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
        )
    }
}

@Composable
private fun DismissButton(onDismiss: () -> Unit) {
    TextButton(onClick = onDismiss) {
        Text(
            text = stringResource(R.string.exit),
        )
    }
}

/**
 * Customise the colors of the date picker
 * @author Simon Francisco
 */
@Composable
private fun DatePickerSettings(
    datePickerState: DatePickerState,
) {
    DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
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