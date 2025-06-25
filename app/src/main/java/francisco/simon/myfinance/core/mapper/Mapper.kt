package francisco.simon.myfinance.core.mapper

import francisco.simon.myfinance.R
import francisco.simon.myfinance.domain.utils.NetworkError
import java.time.Instant
import java.time.LocalDateTime
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

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this)
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