package francisco.simon.myfinance.domain.utils

fun <In, Out> NetworkResult<In>.flatMapIfSuccess(
    block: (In) -> NetworkResult<Out>
): NetworkResult<Out> {
    return when (this) {
        is NetworkResult.Error -> NetworkResult.Error(code = this.code, message = this.message)
        is NetworkResult.Exception -> NetworkResult.Exception(e = this.e)
        is NetworkResult.Success -> block(this.data)
    }
}

private const val SUCCESS_CODE = 200

fun <T> T.toSuccessResult(): NetworkResult.Success<T> = NetworkResult.Success(code = SUCCESS_CODE, data = this)

