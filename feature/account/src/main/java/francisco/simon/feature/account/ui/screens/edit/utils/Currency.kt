package francisco.simon.feature.account.ui.screens.edit.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import francisco.simon.core.ui.R

enum class Currency(
    @StringRes val displayNameRes: Int,
    @DrawableRes val iconRes: Int,
    val symbol: String
) {
    RUB(R.string.russian_ruble, R.drawable.ic_ruble, "₽"),
    USD(R.string.american_dollar, R.drawable.ic_dollar,"$"),
    EUR(R.string.euro, R.drawable.ic_euro, "€")
}