package francisco.simon.myfinance.domain.utils

typealias DomainError = Error

sealed interface NetworkResult<out T> {
    data class Success<out T>(val code: Int, val data: T) : NetworkResult<T>
    data class Error(val code: Int, val message: String) : NetworkResult<Nothing>
    data class Exception(val e: Throwable) : NetworkResult<Nothing>
}