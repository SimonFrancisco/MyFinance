package francisco.simon.myfinance.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

/**
 * Access to nav controller using composition local
 * @author Simon Francisco
 */
val LocalNavController = staticCompositionLocalOf<NavController> {
    error("Can't access NavController")
}