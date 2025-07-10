package francisco.simon.myfinance.core.ui.topBar.topBarUpdate

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.ui.topBar.ActionButton
import francisco.simon.myfinance.core.ui.topBar.AppBarState
import francisco.simon.myfinance.core.ui.topBar.NavigationButton
import francisco.simon.myfinance.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.navigation.CategoryGraph.CategoryRoute
import francisco.simon.myfinance.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.myfinance.navigation.ExpenseGraph.ExpensesHistoryRoute
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeHistoryRoute
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeRoute
import francisco.simon.myfinance.navigation.SettingsGraph.SettingsRoute
import kotlin.reflect.KClass

@Composable
fun UpdateAppBarState(
    appBarState: MutableState<AppBarState>,
    @StringRes titleRes: Int,
    navigationButton: NavigationButton? = null,
    actionButton: ActionButton? = null
) {
    SideEffect {
        appBarState.value = AppBarState(
            titleRes = titleRes,
            navigationButton = navigationButton,
            actionButton = actionButton
        )
    }
}

/**
 * For testing purposes, not production ready because fun size is directly proportional
 * to the number of screens and logic is apart from each screen!
 * @author Simon Francisco
 */
fun appBarStateUpdate(route: KClass<*>?, navController: NavController): AppBarState {
    return when (route) {
        IncomeRoute::class -> {
            AppBarState(
                titleRes = R.string.income_app_top_bar,
                actionButton = ActionButton(R.drawable.ic_history) {
                    navController.navigate(IncomeHistoryRoute)
                }
            )
        }

        IncomeHistoryRoute::class -> {
            AppBarState(
                titleRes = R.string.history_app_top_bar,
                navigationButton = NavigationButton.Back {
                    navController.popBackStack()
                },
                actionButton = ActionButton(R.drawable.ic_analysis) {}
            )
        }

        ExpenseRoute::class -> {
            AppBarState(
                titleRes = R.string.expense_app_top_bar,
                actionButton = ActionButton(R.drawable.ic_history) {
                    navController.navigate(ExpensesHistoryRoute)
                }
            )
        }

        ExpensesHistoryRoute::class -> {
            AppBarState(
                titleRes = R.string.history_app_top_bar,
                navigationButton = NavigationButton.Back {
                    navController.popBackStack()
                },
                actionButton = ActionButton(R.drawable.ic_analysis) {}
            )
        }

        AccountRoute::class -> {
            AppBarState(
                titleRes = R.string.account_app_top_bar,
                actionButton = ActionButton(
                    icon = R.drawable.ic_edit
                ) {} // TODO
            )
        }

        CategoryRoute::class -> {
            AppBarState(
                titleRes = R.string.category_app_top_bar
            )
        }

        SettingsRoute::class -> {
            AppBarState(
                titleRes = R.string.settings_app_top_bar,
            )
        }

        else -> {
            AppBarState(R.string.expense_app_top_bar)
        }
    }
}