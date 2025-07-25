@file:OptIn(ExperimentalMaterial3Api::class)

package francisco.simon.core.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import francisco.simon.core.ui.R
import java.time.LocalTime

@Composable
fun CustomTimePicker(
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(LocalTime.of(timePickerState.hour, timePickerState.minute))
                onDismiss()
            }) {
                Text(stringResource(R.string.confirm_time))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_time_choice))
            }
        },
        title = {
            Text(stringResource(R.string.choose_time))
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}