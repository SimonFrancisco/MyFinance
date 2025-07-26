package francisco.simon.core.domain.preferences

import kotlinx.coroutines.flow.Flow

interface AboutPreferences {
    val appVersion: Flow<String>
    suspend fun setAppVersion(version: String)
}