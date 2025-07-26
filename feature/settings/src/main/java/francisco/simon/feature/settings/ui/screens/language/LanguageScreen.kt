package francisco.simon.feature.settings.ui.screens.language

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.feature.settings.settingsComponent
import java.util.Locale

@Composable
internal fun LanguageScreen(
    appBarState: MutableState<AppBarState>,
    onGoBackToSettingScreen: () -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.language_app_top_bar,
        navigationButton = NavigationButton.Back {
            onGoBackToSettingScreen()
        }
    )
    val component = settingsComponent()
    val viewModel: LanguageScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    LaunchedEffect(Unit) {
        viewModel.getLocale()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    LanguageScreenContent(
        state = currentState,
        viewModel = viewModel
    )
}

@Composable
private fun LanguageScreenContent(
    state: LanguageScreenState,
    viewModel: LanguageScreenViewModel
) {
    // TODO use enum
    val locales = listOf(
        "en" to "English",
        "ru" to "Русский"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is LanguageScreenState.Loading -> {
                FullScreenLoading()
            }

            is LanguageScreenState.Success -> {
                locales.forEach { (code, label) ->
                    val selected = state.locale.language == code
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selected,
                                onClick = { viewModel.setLocale(Locale(code)) }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = selected,
                            onClick = { viewModel.setLocale(Locale(code)) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

// TODO continue later
internal enum class Language(code: String, @StringRes displayName: Int)