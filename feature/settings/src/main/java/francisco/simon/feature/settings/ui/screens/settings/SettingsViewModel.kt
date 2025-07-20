package francisco.simon.feature.settings.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for settings, works with state
 * @author Simon Francisco
 */
internal class SettingsViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<SettingsScreenState> = MutableStateFlow(SettingsScreenState.Nothing)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SettingsScreenState.Nothing
        )
}