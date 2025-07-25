package francisco.simon.myfinance.domain

import francisco.simon.core.domain.preferences.ThemeModePreferences
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val themeModePreferences: ThemeModePreferences
) {
    operator fun invoke(): Flow<MyFinanceThemeMode> {
        return themeModePreferences.themeMode
    }
}