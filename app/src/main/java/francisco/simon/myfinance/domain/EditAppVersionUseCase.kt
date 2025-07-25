package francisco.simon.myfinance.domain

import francisco.simon.core.domain.preferences.AboutPreferences
import javax.inject.Inject

class EditAppVersionUseCase @Inject constructor(
    private val aboutPreferences: AboutPreferences
) {
    suspend operator fun invoke(appVersion: String) {
        aboutPreferences.setAppVersion(appVersion)
    }
}