package francisco.simon.feature.account.ui.model

import androidx.annotation.DrawableRes
import francisco.simon.core.ui.R
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