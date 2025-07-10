package francisco.simon.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow

object MonitorAccount {
    private val _version = MutableStateFlow(0)
    val version = _version

    fun event(command: Commands) {
        when (command) {
            Commands.UPDATE_ACCOUNT -> {
                _version.value += 1
            }
        }
    }

    enum class Commands {
        UPDATE_ACCOUNT
    }
}

/**
 * Propagate change only if account was edited.
 * This doesn't affect just changing tabs without changes in account.
 * @author Simon Francisco
 */
@Composable
fun UpdateWhenGoingBack(onGoBack: () -> Unit) {
    val version by MonitorAccount.version.collectAsStateWithLifecycle()
    var lastSeenVersion by rememberSaveable { mutableIntStateOf(version) }

    LaunchedEffect(version) {
        if (version > lastSeenVersion) {
            lastSeenVersion = version
            onGoBack()
        }
    }
}