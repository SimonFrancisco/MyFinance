package francisco.simon.feature.settings.ui.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.feature.settings.domain.about.getLastUpdateDate
import francisco.simon.feature.settings.settingsComponent

@Composable
internal fun AboutScreen(
    appBarState: MutableState<AppBarState>,
    onGoBackToSettingScreen: () -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.about_app_top_bar,
        navigationButton = NavigationButton.Back {
            onGoBackToSettingScreen()
        }
    )
    val component = settingsComponent()
    val viewModel: AboutScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    LaunchedEffect(Unit) {
        viewModel.getAppVersion()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    AboutScreenContent(
        state = currentState
    )
}

@Composable
private fun AboutScreenContent(
    state: AboutScreenState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        when (state) {
            is AboutScreenState.Loading -> {
                FullScreenLoading()
            }

            is AboutScreenState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.app_verison),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.appVersion,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.app_last_update),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = getLastUpdateDate(context),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }
    }
}