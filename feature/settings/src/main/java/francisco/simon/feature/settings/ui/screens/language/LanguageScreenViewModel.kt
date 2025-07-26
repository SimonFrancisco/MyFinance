package francisco.simon.feature.settings.ui.screens.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.feature.settings.domain.language.EditLocaleUseCase
import francisco.simon.feature.settings.domain.language.GetLocaleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

internal class LanguageScreenViewModel @Inject constructor(
    private val getLocaleUseCase: GetLocaleUseCase,
    private val editLocaleUseCase: EditLocaleUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LanguageScreenState> =
        MutableStateFlow(LanguageScreenState.Loading)
    val state: StateFlow<LanguageScreenState> = _state

    fun setLocale(locale: Locale) {
        viewModelScope.launch {
            editLocaleUseCase(locale.language)
        }
    }

    fun getLocale() {
        viewModelScope.launch {
            _state.update {
                LanguageScreenState.Loading
            }
            getLocaleUseCase().collectLatest { locale ->
                _state.update {
                    LanguageScreenState.Success(locale)
                }
            }

        }

    }
}