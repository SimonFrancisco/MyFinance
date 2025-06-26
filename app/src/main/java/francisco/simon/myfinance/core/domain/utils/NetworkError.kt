package francisco.simon.myfinance.core.domain.utils

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    UNKNOWN,
    NULL,
}