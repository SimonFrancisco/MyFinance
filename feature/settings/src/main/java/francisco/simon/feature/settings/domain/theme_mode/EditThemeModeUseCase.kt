package francisco.simon.feature.settings.domain.theme_mode

import francisco.simon.core.domain.preferences.ThemeModePreferences
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import javax.inject.Inject

internal class EditThemeModeUseCase @Inject constructor(
    private val themeModePreferences: ThemeModePreferences
) {
    suspend operator fun invoke(myFinanceThemeMode: MyFinanceThemeMode) {
        themeModePreferences.setThemeMode(myFinanceThemeMode)
    }
}