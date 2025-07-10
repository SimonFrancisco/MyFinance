package francisco.simon.core.data.data_source

import francisco.simon.core.data.data_source.local.model.CategoryDbModel
import francisco.simon.core.data.dto.CategoryDto
import francisco.simon.core.domain.entity.Category

fun CategoryDbModel.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun List<CategoryDbModel>.toListCategory(): List<Category> {
    return this.map {
        it.toCategory()
    }
}

fun CategoryDto.toCategoryDbModel(): CategoryDbModel {
    return CategoryDbModel(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}