package francisco.simon.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow

object MonitorTransaction {

    private val _versionIncome = MutableStateFlow(0)
    val versionIncome = _versionIncome

    private val _versionExpense = MutableStateFlow(0)
    val versionExpense = _versionExpense

    fun event(command: Commands) {
        when (command) {
            Commands.MANIPULATE_INCOME -> {
                _versionIncome.value += 1
            }

            Commands.MANIPULATE_EXPENSE -> {
                _versionExpense.value += 1

            }
        }
    }

    enum class Commands {
        MANIPULATE_INCOME,
        MANIPULATE_EXPENSE

    }
}

/**
 * Propagate change only if income was added/edited.
 * This doesn't affect just changing tabs without changes in account.
 * @author Simon Francisco
 */
@Composable
fun PropagateIncomeChangeUpdateWhenGoingBack(onGoBack: () -> Unit) {
    val version by MonitorTransaction.versionIncome.collectAsStateWithLifecycle()
    var lastSeenVersion by rememberSaveable { mutableIntStateOf(version) }

    LaunchedEffect(version) {
        if (version > lastSeenVersion) {
            lastSeenVersion = version
            onGoBack()
        }
    }
}

/**
 * Propagate change only if expense was added/edited.
 * This doesn't affect just changing tabs without changes in account.
 * @author Simon Francisco
 */
@Composable
fun PropagateExpenseChangeUpdateWhenGoingBack(onGoBack: () -> Unit) {
    val version by MonitorTransaction.versionExpense.collectAsStateWithLifecycle()
    var lastSeenVersion by rememberSaveable { mutableIntStateOf(version) }

    LaunchedEffect(version) {
        if (version > lastSeenVersion) {
            lastSeenVersion = version
            onGoBack()
        }
    }
}