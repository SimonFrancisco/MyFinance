package francisco.simon.feature.settings.domain.color_scheme

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