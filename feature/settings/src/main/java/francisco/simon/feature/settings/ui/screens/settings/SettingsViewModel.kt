package francisco.simon.feature.settings.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import francisco.simon.feature.settings.domain.theme_mode.EditThemeModeUseCase
import francisco.simon.feature.settings.domain.theme_mode.GetThemeModeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for settings, works with state
 * @author Simon Francisco
 */
internal class SettingsViewModel @Inject constructor(
    private val editThemeModeUseCase: EditThemeModeUseCase,
    private val getThemeModeUseCase: GetThemeModeUseCase
) : ViewModel() {

    val state: StateFlow<SettingsScreenState> = MutableStateFlow(SettingsScreenState.Nothing)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SettingsScreenState.Nothing
        )

    private val _themeMode = MutableStateFlow(MyFinanceThemeMode.LIGHT)
    val themeMode: StateFlow<MyFinanceThemeMode> = _themeMode

    init {
        getThemeMode()
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            getThemeModeUseCase().collectLatest { themeMode ->
                _themeMode.update {
                    themeMode
                }
            }
        }
    }

    fun setTheme(themeMode: MyFinanceThemeMode) {
        viewModelScope.launch {
            editThemeModeUseCase(themeMode)
        }
    }

}