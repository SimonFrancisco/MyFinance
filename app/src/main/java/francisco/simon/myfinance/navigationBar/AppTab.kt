package francisco.simon.myfinance.navigationBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import francisco.simon.feature.account.navigation.AccountGraph
import francisco.simon.feature.category.navigation.CategoryRouteGraph
import francisco.simon.feature.expenses.navigation.ExpenseGraph
import francisco.simon.feature.income.navigation.IncomeGraph
import francisco.simon.feature.settings.navigation.SettingsGraph
import francisco.simon.core.ui.R

/***
 * Tabs for app navigation bar, each tab has an icon, a label and the graph
 * it should be attached to!
 *
 * @author Simon Francisco
 */

internal data class AppTab(
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
    val graph: Any
)

/**
 * List of tabs we see in nav bar
 * @author Simon Francisco
 */
internal val mainTabs = listOf<AppTab>(
    AppTab(
        iconRes = R.drawable.ic_downtrend,
        labelRes = R.string.expenses_tab,
        graph = ExpenseGraph
    ),
    AppTab(
        iconRes = R.drawable.ic_uptrend,
        labelRes = R.string.income_tab,
        graph = IncomeGraph
    ),
    AppTab(
        iconRes = R.drawable.ic_calculator,
        labelRes = R.string.account_tab,
        graph = AccountGraph
    ),
    AppTab(
        iconRes = R.drawable.ic_bar_chart_side,
        labelRes = R.string.category_tab,
        graph = CategoryRouteGraph
    ),
    AppTab(
        iconRes = R.drawable.ic_settings,
        labelRes = R.string.settings_tab,
        graph = SettingsGraph
    )
)