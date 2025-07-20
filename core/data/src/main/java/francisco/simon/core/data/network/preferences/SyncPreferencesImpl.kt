package francisco.simon.core.data.network.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import francisco.simon.core.domain.preferences.SyncPreferences


class SyncPreferencesImpl(
    context: Context
) : SyncPreferences {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SyncPreferences", Context.MODE_PRIVATE)

    override fun saveLastSyncTime(time: Long) {
        sharedPreferences.edit { putLong(LAST_SYNC_KEY, time) }
    }

    override fun getLastSyncTime(): Long {
        return sharedPreferences.getLong(LAST_SYNC_KEY, 0L)
    }

    companion object {
        private const val LAST_SYNC_KEY = "last_sync_time"
    }
}