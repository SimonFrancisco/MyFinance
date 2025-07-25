package francisco.simon.core.domain.preferences

import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeModePreferences {
    val themeMode: Flow<MyFinanceThemeMode>
    suspend fun setThemeMode(themeMode: MyFinanceThemeMode)
}