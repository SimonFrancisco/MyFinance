package francisco.simon.myfinance.data.api

import francisco.simon.myfinance.domain.utils.NetworkError
import francisco.simon.myfinance.domain.utils.NetworkResult
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCall(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(code = response.code(), data = body)
        } else {
            NetworkResult.Error(
                code = response.code(),
                message = response.errorBody().toString()
            )
        }
    } catch (e: HttpException) {
        NetworkResult.Error(e.code(), e.message())
    } catch (e: IOException) {
        NetworkResult.Exception(e)
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}

