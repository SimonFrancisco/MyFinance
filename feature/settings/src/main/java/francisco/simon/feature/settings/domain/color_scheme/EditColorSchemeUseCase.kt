package francisco.simon.feature.settings.domain.color_scheme

import francisco.simon.core.domain.preferences.ColorSchemePreferences
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import javax.inject.Inject

internal class EditColorSchemeUseCase @Inject constructor(
    private val colorSchemePreferences: ColorSchemePreferences
) {
    suspend operator fun invoke(myFinanceColorScheme: MyFinanceColorScheme) {
        colorSchemePreferences.setColorScheme(myFinanceColorScheme)
    }
}