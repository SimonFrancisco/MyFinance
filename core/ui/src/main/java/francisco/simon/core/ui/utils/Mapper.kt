package francisco.simon.core.ui.utils

import francisco.simon.core.domain.utils.NetworkError
import francisco.simon.core.ui.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Mappers used across the app
 *
 * @author Simon Francisco
 */
fun String.toCurrencySymbol(): String {
    return when (this) {
        "USD" -> "$"
        "EUR" -> "€"
        "RUB" -> "₽"
        else -> this
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

fun LocalDate.toDateWritten(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'")
    return this.format(dateFormatter)
}

fun String.toDateAndTime(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm")
    val instant = Instant.parse(this)
    return instant.atOffset(ZoneOffset.UTC).toLocalDateTime().format(dateFormat)
}

fun LocalDateTime.toDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return this.format(dateFormat)
}

fun LocalDateTime.toTime(): String {
    val dateFormat = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(dateFormat)
}

fun LocalDateTime.toTransactionModelTime(): String {
    return this.toInstant(ZoneOffset.UTC)
        .toString()
}

fun String.toLocalDateTime(): LocalDateTime {
    return Instant.parse(this).atOffset(ZoneOffset.UTC).toLocalDateTime()
}

/**
 * Map network errors to string resources
 *
 * @author Simon Francisco
 */
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