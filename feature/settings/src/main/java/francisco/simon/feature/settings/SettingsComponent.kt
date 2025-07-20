package francisco.simon.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import francisco.simon.feature.settings.di.DaggerSettingsComponent
import francisco.simon.feature.settings.di.SettingsComponent
import francisco.simon.feature.settings.di.SettingsDependenciesProvider

// TODO make component live as longs as the feature lives

@Composable
internal fun settingsComponent(): SettingsComponent {
    val context = LocalContext.current.applicationContext
    val settingsDependencies = remember {
        (context as SettingsDependenciesProvider).getSettingsDependencies()
    }
    val component = remember {
        DaggerSettingsComponent.builder().settingsDependencies(settingsDependencies).build()
    }
    return component
}