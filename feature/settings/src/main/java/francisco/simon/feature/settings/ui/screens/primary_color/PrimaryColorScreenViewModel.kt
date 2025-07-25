package francisco.simon.feature.settings.ui.screens.primary_color

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import francisco.simon.feature.settings.domain.color_scheme.EditColorSchemeUseCase
import francisco.simon.feature.settings.domain.color_scheme.GetColorSchemeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PrimaryColorScreenViewModel @Inject constructor(
    private val getColorSchemeUseCase: GetColorSchemeUseCase,
    private val editColorSchemeUseCase: EditColorSchemeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<PrimaryColorScreenState> =
        MutableStateFlow(PrimaryColorScreenState.Loading)
    val state: StateFlow<PrimaryColorScreenState> = _state


    fun getColorScheme() {
        _state.update {
            PrimaryColorScreenState.Loading
        }
        viewModelScope.launch {
            getColorSchemeUseCase().collectLatest { colorScheme ->
                _state.update {
                    PrimaryColorScreenState.Success(colorScheme)
                }
            }
        }
    }

    fun setColorScheme(colorScheme: MyFinanceColorScheme) {
        viewModelScope.launch {
            editColorSchemeUseCase(colorScheme)
        }
    }
}