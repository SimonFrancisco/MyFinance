package francisco.simon.feature.settings.ui.screens.primary_color

import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme

sealed interface PrimaryColorScreenState {
    data object Loading : PrimaryColorScreenState
    data class Success(val colorScheme: MyFinanceColorScheme) : PrimaryColorScreenState
}