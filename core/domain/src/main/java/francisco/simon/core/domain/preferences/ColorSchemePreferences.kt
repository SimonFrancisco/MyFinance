package francisco.simon.core.domain.preferences

import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import kotlinx.coroutines.flow.Flow

interface ColorSchemePreferences {
    val colorScheme: Flow<MyFinanceColorScheme>
    suspend fun setColorScheme(colorScheme: MyFinanceColorScheme)
}