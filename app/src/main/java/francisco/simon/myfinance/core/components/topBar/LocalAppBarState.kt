package francisco.simon.myfinance.core.components.topBar

import androidx.compose.runtime.staticCompositionLocalOf
import francisco.simon.myfinance.R

val LocalAppBarState = staticCompositionLocalOf <AppBarState> {
    AppBarState(R.string.app_name)
}