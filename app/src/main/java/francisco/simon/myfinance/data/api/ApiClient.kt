package francisco.simon.myfinance.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import francisco.simon.myfinance.core.domain.utils.NetworkError
import francisco.simon.myfinance.core.domain.utils.Result
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun <T> safeApiCall(
        execute: suspend () -> Response<T>,
    ): Result<T, NetworkError> {
        if (!isNetworkAvailable()) {
            return Result.Error(NetworkError.NO_INTERNET)
        }
        var currentAttempt = 0
        while (currentAttempt <= MAX_RETRY_ATTEMPTS) {
            val response = try {
                execute()
            } catch (e: Exception) {
                currentCoroutineContext().ensureActive()
                return Result.Error(NetworkError.UNKNOWN)
            }
            when (response.code()) {
                in SUCCESS_CODE_RANGE -> {
                    val body = response.body()
                    return if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Error(NetworkError.NULL)
                    }
                }
                REQUEST_TIMEOUT_CODE -> return Result.Error(NetworkError.REQUEST_TIMEOUT)
                TOO_MANY_REQUESTS_CODE -> return Result.Error(NetworkError.TOO_MANY_REQUESTS)
                in SERVER_ERROR_CODE_RANGE -> {
                    if (currentAttempt == MAX_RETRY_ATTEMPTS) {
                        return Result.Error(NetworkError.SERVER_ERROR)
                    }
                    delay(RETRY_INTERVAL)
                    currentAttempt++
                    continue
                }

                else -> return Result.Error(NetworkError.UNKNOWN)
            }
        }
        return Result.Error(NetworkError.UNKNOWN)

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.run {
                hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        } ?: false

    }

    private companion object {
        const val MAX_RETRY_ATTEMPTS = 3
        const val RETRY_INTERVAL = 2000L
        const val REQUEST_TIMEOUT_CODE = 408
        const val TOO_MANY_REQUESTS_CODE = 429
        val SUCCESS_CODE_RANGE = 200..299
        val SERVER_ERROR_CODE_RANGE = 500..599
    }
}



