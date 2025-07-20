package francisco.simon.feature.settings.ui.screens.sync

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.feature.settings.settingsComponent

@Composable
internal fun SyncScreen(
    appBarState: MutableState<AppBarState>,
    onGoBackToSettingScreen: () -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.sync_app_top_bar,
        navigationButton = NavigationButton.Back {
            onGoBackToSettingScreen()
        }
    )
    val component = settingsComponent()
    val viewModel: SyncScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    LaunchedEffect(Unit) {
        viewModel.loadLastSyncTime()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    SyncScreenContent(
        state = currentState
    )
}

@Composable
private fun SyncScreenContent(
    state: SyncScreenState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is SyncScreenState.Empty -> {
                Text(
                    text = stringResource(R.string.no_sync_time),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            is SyncScreenState.Loading -> {
                FullScreenLoading()
            }

            is SyncScreenState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.last_sync_time),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.time,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }
    }
}