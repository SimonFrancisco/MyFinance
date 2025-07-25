package francisco.simon.myfinance.domain

import francisco.simon.core.domain.preferences.ColorSchemePreferences
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetColorSchemeUseCase @Inject constructor(
    private val colorSchemePreferences: ColorSchemePreferences
) {
    operator fun invoke(): Flow<MyFinanceColorScheme> {
        return colorSchemePreferences.colorScheme
    }
}