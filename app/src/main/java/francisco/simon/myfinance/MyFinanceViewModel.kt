package francisco.simon.myfinance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import francisco.simon.feature.settings.domain.theme_mode.GetThemeModeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MyFinanceViewModel @Inject constructor(
    getThemeModeUseCase: GetThemeModeUseCase
) : ViewModel() {

    val themeMode: StateFlow<MyFinanceThemeMode> =
        getThemeModeUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = MyFinanceThemeMode.LIGHT
            )
}