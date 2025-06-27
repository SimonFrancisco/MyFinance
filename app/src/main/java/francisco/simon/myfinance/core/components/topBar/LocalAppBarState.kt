package francisco.simon.myfinance.core.components.topBar

import androidx.compose.runtime.staticCompositionLocalOf
import francisco.simon.myfinance.R

/**
 * App bar state using composition locals
 * @author Simon Francisco
 */
val LocalAppBarState = staticCompositionLocalOf <AppBarState> {
    AppBarState(R.string.app_name)
}