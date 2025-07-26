package francisco.simon.feature.settings.domain.about

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun getLastUpdateDate(context: Context): String {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val lastUpdateTime = packageInfo.lastUpdateTime
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(lastUpdateTime))
}
