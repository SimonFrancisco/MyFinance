package francisco.simon.feature.settings.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.feature.settings.settingsComponent

/**
 * Settings Screen, separate concerns to avoid unnecessary recompositions and
 * keep code logic short
 * @author Simon Francisco
 */
@Composable
internal fun SettingsScreen(appBarState: MutableState<AppBarState>, onGoToSync: () -> Unit) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.settings_app_top_bar,
    )
    val component = settingsComponent()
    val viewModel: SettingsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    SettingsScreenContent(currentState, onClick = onGoToSync)
}

@Composable
private fun SettingsScreenContent(
    state: SettingsScreenState,
    onClick: () -> Unit
) {
    when (state) {
        SettingsScreenState.Nothing -> {
            SettingsScreenList(fakeSettings, onClick)
        }
    }
}


@Composable
private fun SettingsScreenList(
    settingsOptions: List<String> = fakeSettings,
    onClick: () -> Unit
) {
    val checked = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            item {
                SwitchItem(checked)
                HorizontalDivider()
            }
            items(settingsOptions) { setting ->
                if (setting != "Синхронизация") { // TODO  This is костыль, remove later
                    SettingsItem(setting) {
                    }
                } else {
                    SettingsItem(setting) {
                        onClick()
                    }
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun SettingsItem(setting: String, onClick: () -> Unit) {
    francisco.simon.core.ui.components.CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .clickable {
                onClick()
            },
        headlineContent = {
            Text(text = setting, style = MaterialTheme.typography.bodyLarge)
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        },
    )
}

@Composable
private fun SwitchItem(checked: MutableState<Boolean>) {
    francisco.simon.core.ui.components.CustomListItem(
        modifier = Modifier
            .height(56.dp),
        headlineContent = {
            Text(
                text = stringResource(R.string.dark_theme),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            // TODO change color
            Switch(
                modifier = Modifier
                    .height(32.dp)
                    .width(52.dp),
                checked = checked.value, onCheckedChange = {
                    checked.value = it
                }
            )
        },
    )
}

/**
 * Create fake settings for now
 * @author Simon Francisco
 */
private val fakeSettings = listOf(
    "Основной цвет",
    "Звуки",
    "Хаптики",
    "Код пароль",
    "Синхронизация",
    "Язык",
    "О программе",
)