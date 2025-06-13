package francisco.simon.myfinance.ui.features.category.model

import androidx.annotation.DrawableRes

data class CategoryUI(
    val id: Int,
    @DrawableRes val emojiRes: Int,
    val name: String
)
