package francisco.simon.myfinance.core.components.navigationBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import francisco.simon.myfinance.R
import francisco.simon.myfinance.ui.navigation.AccountGraph
import francisco.simon.myfinance.ui.navigation.CategoryGraph
import francisco.simon.myfinance.ui.navigation.ExpenseGraph
import francisco.simon.myfinance.ui.navigation.IncomeGraph
import francisco.simon.myfinance.ui.navigation.SettingsGraph


data class AppTab(
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
    val graph: Any
)

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