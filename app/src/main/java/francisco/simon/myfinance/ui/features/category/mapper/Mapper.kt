package francisco.simon.myfinance.ui.features.category.mapper

import francisco.simon.myfinance.core.mapper.toVectorRes
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.ui.features.category.model.CategoryUI


fun Category.toCategoryUI(): CategoryUI {
    return CategoryUI(
        id = id,
        emojiRes = emoji.toVectorRes(),
        name = name
    )
}

fun List<Category>.toListCategoryUI(): List<CategoryUI> {
    return this.map {
        it.toCategoryUI()
    }
}