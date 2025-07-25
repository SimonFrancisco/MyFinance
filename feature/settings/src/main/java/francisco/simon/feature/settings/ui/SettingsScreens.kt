package francisco.simon.feature.settings.ui

import androidx.annotation.StringRes
import francisco.simon.core.ui.R

internal enum class SettingScreen(
    @StringRes val displayNameRes: Int,
) {
    PRIMARY_COLOR(R.string.settings_primary_color),
    VIBRATION(R.string.settings_vibration),
    PIN_CODE(R.string.settings_pin_code),
    SYNCHRONIZATION(R.string.settings_synchronization),
    LANGUAGE(R.string.settings_language),
    ABOUT(R.string.settings_about_app)
}