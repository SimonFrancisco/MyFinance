package francisco.simon.myfinance.ui.features.category.mapper

import francisco.simon.core.domain.entity.Category
import francisco.simon.myfinance.ui.features.category.model.CategoryUI

/**
 *  Mapper from domain to ui
 *  @author Simon Francisco
 */

fun Category.toCategoryUI(): CategoryUI {
    return CategoryUI(
        id = id,
        emoji = emoji,
        name = name,
    )
}

fun List<Category>.toListCategoryUI(): List<CategoryUI> {
    return this.map {
        it.toCategoryUI()
    }
}