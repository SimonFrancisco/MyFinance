package francisco.simon.myfinance.ui.features.account.model

import androidx.annotation.DrawableRes
import francisco.simon.myfinance.R
import java.math.BigDecimal

/**
 * Separate UI model
 * @author Simon Francisco
 */
data class AccountUI (
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: String,
    @DrawableRes val emojiRes:Int = R.drawable.ic_money_bag,
)