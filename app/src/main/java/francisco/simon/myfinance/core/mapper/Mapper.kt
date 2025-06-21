package francisco.simon.myfinance.core.mapper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun String.toCurrencySymbol(): String {
    return when (this) {
        "RUB" -> "₽"
        else -> ""
    }
}

fun Instant.toApiDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.atZone(ZoneId.systemDefault()).toLocalDate().format(dateFormat)
}