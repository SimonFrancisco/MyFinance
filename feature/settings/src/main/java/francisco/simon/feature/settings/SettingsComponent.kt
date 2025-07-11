package francisco.simon.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import francisco.simon.feature.settings.di.DaggerSettingsComponent
import francisco.simon.feature.settings.di.SettingsComponent

// TODO make component live as longs as the feature lives

@Composable
internal fun settingsComponent(): SettingsComponent {
    val component = remember {
        DaggerSettingsComponent.create()
    }
    return component
}