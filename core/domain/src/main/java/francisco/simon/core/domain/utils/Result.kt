package francisco.simon.core.domain.utils

/**
 * Result class and mappers for all kinds of operations
 * Two subtypes: Success and Error
 * @author Simon Francisco
 */
typealias RootError = Error
typealias EmptyResult<E> = Result<Unit, E>

sealed interface Result<out T, out E : RootError> {
    data class Success<out T>(val data: T) : Result<T, Nothing>
    data class Error<out E : RootError>(val error: E) : Result<Nothing, E>
}

/**
 * Maps Result and returns Result with mapped value
 * @author Simon Francisco
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Gives access to object found in Success
 * @author Simon Francisco
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Gives access to returned error
 * @author Simon Francisco
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/**
 * If no result is empty
 * @author Simon Francisco
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {}
}
