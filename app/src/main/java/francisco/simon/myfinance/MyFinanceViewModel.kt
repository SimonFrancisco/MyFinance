package francisco.simon.myfinance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import francisco.simon.myfinance.domain.EditAppVersionUseCase
import francisco.simon.myfinance.domain.GetColorSchemeUseCase
import francisco.simon.myfinance.domain.GetLocaleUseCase
import francisco.simon.myfinance.domain.GetThemeModeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

internal class MyFinanceViewModel @Inject constructor(
    getThemeModeUseCase: GetThemeModeUseCase,
    getColorSchemeUseCase: GetColorSchemeUseCase,
    getLocaleUseCase: GetLocaleUseCase,
    private val editAppVersionUseCase: EditAppVersionUseCase
) : ViewModel() {

    val themeMode: StateFlow<MyFinanceThemeMode> =
        getThemeModeUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = MyFinanceThemeMode.LIGHT
            )

    val colorScheme: StateFlow<MyFinanceColorScheme> =
        getColorSchemeUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = MyFinanceColorScheme.GREEN
            )

    val locale: StateFlow<Locale> =
        getLocaleUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = Locale.getDefault()
            )

    fun setApiVersion(appVersion: String) {
        viewModelScope.launch {
            editAppVersionUseCase(appVersion)
        }
    }

}