package francisco.simon.feature.category.ui.mapper

import francisco.simon.core.domain.entity.Category
import francisco.simon.feature.category.ui.model.CategoryUI

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