package francisco.simon.myfinance.core.mapper

import francisco.simon.myfinance.R
import francisco.simon.myfinance.domain.utils.NetworkError
import java.time.Instant
import java.time.LocalDate
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

fun LocalDate.toApiDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.format(dateFormat)
}

fun LocalDate.toDateWritten():String{
    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'")
    return this.format(dateFormatter)
}

fun String.toDateAndTime(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm")
    val instant = Instant.parse(this)
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateFormat)
}


fun NetworkError.toStringRes(): Int {
    return when (this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.UNKNOWN -> R.string.error_unknown
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.SERVER_ERROR -> R.string.error_server
        NetworkError.NULL -> R.string.error_null
    }
}