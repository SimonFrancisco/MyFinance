package francisco.simon.core.domain.utils

/**
 * List of possible network errors
 * @author Simon Francisco
 */
enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    UNKNOWN,
    NULL,
}