package francisco.simon.feature.settings.ui.mappers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Long.toReadableTime(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(date)
}