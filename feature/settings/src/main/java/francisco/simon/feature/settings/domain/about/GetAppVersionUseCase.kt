package francisco.simon.feature.settings.domain.about

import francisco.simon.core.domain.preferences.AboutPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetAppVersionUseCase @Inject constructor(
    private val aboutPreferences: AboutPreferences
) {
    operator fun invoke(): Flow<String> {
        return aboutPreferences.appVersion
    }
}