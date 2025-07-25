package francisco.simon.feature.settings.ui.screens.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.feature.settings.domain.about.GetAppVersionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class AboutScreenViewModel @Inject constructor(
    private val getAppVersionUseCase: GetAppVersionUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AboutScreenState> =
        MutableStateFlow(AboutScreenState.Loading)
    val state: StateFlow<AboutScreenState> = _state

    fun getAppVersion() {
        viewModelScope.launch {
            _state.update {
                AboutScreenState.Loading
            }
            getAppVersionUseCase().collectLatest { appVersion ->
                _state.update {
                    AboutScreenState.Success(appVersion)
                }
            }

        }

    }
}