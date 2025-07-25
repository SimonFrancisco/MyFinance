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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.feature.settings.settingsComponent
import francisco.simon.feature.settings.ui.SettingScreen

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
    val themeMode = viewModel.themeMode.collectAsStateWithLifecycle()
    val isDarkTheme = themeMode.value == MyFinanceThemeMode.DARK
    val currentState = state.value
    SettingsScreenContent(
        state = currentState,
        onClick = { settingScreen ->
            when(settingScreen){
                SettingScreen.PRIMARY_COLOR -> Unit
                SettingScreen.VIBRATION -> Unit
                SettingScreen.PIN_CODE -> Unit
                SettingScreen.SYNCHRONIZATION -> {
                    onGoToSync()
                }
                SettingScreen.LANGUAGE -> Unit
                SettingScreen.ABOUT -> Unit
            }
        },
        isDarkTheme = isDarkTheme,
        onSelectedThemeMode = { selectedThemeMode ->
            val newThemeMode = if (selectedThemeMode) {
                MyFinanceThemeMode.DARK
            } else {
                MyFinanceThemeMode.LIGHT
            }
            viewModel.setTheme(newThemeMode)
        }
    )
}

@Composable
private fun SettingsScreenContent(
    state: SettingsScreenState,
    onClick: (SettingScreen) -> Unit,
    isDarkTheme: Boolean,
    onSelectedThemeMode: (Boolean) -> Unit,
) {
    when (state) {
        SettingsScreenState.Nothing -> {
            SettingsScreenList(
                onClick = onClick,
                isDarkTheme = isDarkTheme,
                onSelectedThemeMode = onSelectedThemeMode
            )
        }
    }
}


@Composable
private fun SettingsScreenList(
    onClick: (SettingScreen) -> Unit,
    isDarkTheme: Boolean,
    onSelectedThemeMode: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            item {
                SwitchItem(
                    isDarkTheme = isDarkTheme,
                    onSelectedThemeMode = onSelectedThemeMode
                )
                HorizontalDivider()
            }
            items(SettingScreen.entries, key = { it.displayNameRes }) { settingScreen ->
                SettingsItem(
                    settingScreen = settingScreen,
                    onClick = {
                        onClick(settingScreen)
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun SettingsItem(
    settingScreen: SettingScreen, onClick: () -> Unit
) {
    CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .clickable {
                onClick()
            },
        headlineContent = {
            Text(
                text = stringResource(settingScreen.displayNameRes),
                style = MaterialTheme.typography.bodyLarge
            )
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
private fun SwitchItem(
    isDarkTheme: Boolean,
    onSelectedThemeMode: (Boolean) -> Unit
) {
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
                checked = isDarkTheme, onCheckedChange = onSelectedThemeMode
            )
        },
    )
}
