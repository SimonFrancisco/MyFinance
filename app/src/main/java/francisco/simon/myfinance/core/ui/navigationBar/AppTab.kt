package francisco.simon.myfinance.core.ui.navigationBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import francisco.simon.myfinance.R
import francisco.simon.myfinance.navigation.AccountGraph
import francisco.simon.myfinance.navigation.CategoryGraph
import francisco.simon.myfinance.navigation.ExpenseGraph
import francisco.simon.myfinance.navigation.IncomeGraph
import francisco.simon.myfinance.navigation.SettingsGraph

/***
 * Tabs for app navigation bar, each tab has an icon, a label and the graph
 * it should be attached to!
 *
 * @author Simon Francisco
 */

data class AppTab(
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
    val graph: Any
)

/**
 * List of tabs we see in nav bar
 * @author Simon Francisco
 */
val mainTabs = listOf<AppTab>(
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
        graph = CategoryGraph
    ),
    AppTab(
        iconRes = R.drawable.ic_settings,
        labelRes = R.string.settings_tab,
        graph = SettingsGraph
    )
)