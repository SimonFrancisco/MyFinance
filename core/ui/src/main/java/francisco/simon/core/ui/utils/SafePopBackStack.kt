package francisco.simon.core.ui.utils

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

fun NavController.safePopBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}