package francisco.simon.myfinance.ui.features.account.model

import androidx.annotation.DrawableRes
import francisco.simon.myfinance.R

data class AccountUI (
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    @DrawableRes val emojiRes:Int = R.drawable.ic_money_bag
)