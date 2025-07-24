package francisco.simon.core.ui.analyis.graph.mapper

import francisco.simon.core.domain.model.CategoryStatsModel
import francisco.simon.core.ui.analyis.graph.generateColorForCategory
import francisco.simon.core.ui.analyis.graph.model.CategoryGraphModelUi

internal fun CategoryStatsModel.toCategoryGraphModelUi(): CategoryGraphModelUi {
    return CategoryGraphModelUi(
        name = name,
        percentage = percent,
        color = generateColorForCategory(categoryId = categoryId)
    )
}

internal fun List<CategoryStatsModel>.toListCategoryGraphModel(): List<CategoryGraphModelUi> {
    return this.map {
        it.toCategoryGraphModelUi()
    }
}