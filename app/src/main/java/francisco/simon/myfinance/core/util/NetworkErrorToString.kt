package francisco.simon.myfinance.core.util

import android.content.Context
import francisco.simon.myfinance.R
import francisco.simon.myfinance.domain.utils.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}