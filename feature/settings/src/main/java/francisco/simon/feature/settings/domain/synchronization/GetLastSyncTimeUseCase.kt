package francisco.simon.feature.settings.domain.synchronization

import francisco.simon.core.domain.preferences.SyncPreferences
import javax.inject.Inject

internal class GetLastSyncTimeUseCase @Inject constructor(
    private val syncPreferences: SyncPreferences
) {
    operator fun invoke(): Long {
        return syncPreferences.getLastSyncTime()
    }
}