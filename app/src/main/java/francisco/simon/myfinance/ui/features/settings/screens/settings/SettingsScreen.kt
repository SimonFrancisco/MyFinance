package francisco.simon.myfinance.ui.features.settings.screens.settings

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.topBar.AppBarState

@Composable
fun SettingsScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.settings_app_top_bar,
            )
        )
    }
    val viewModel: SettingsViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    SettingsScreenContent(currentState)
}

@Composable
private fun SettingsScreenContent(
    state: SettingsScreenState
) {
    when (state) {
        SettingsScreenState.Nothing -> {
            SettingsScreenList(fakeSettings)
        }
    }
}


@Composable
private fun SettingsScreenList(
    settingsOptions: List<String> = fakeSettings
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
                SettingsItem(setting)
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun SettingsItem(setting: String) {
    CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .clickable {

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
    CustomListItem(
        modifier = Modifier
            .height(56.dp),
        headlineContent = {
            Text(
                text = stringResource(R.string.dark_theme),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
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

private val fakeSettings = listOf(
    "Основной цвет",
    "Звуки",
    "Хаптики",
    "Код пароль",
    "Синхронизация",
    "Язык",
    "О программе",
)