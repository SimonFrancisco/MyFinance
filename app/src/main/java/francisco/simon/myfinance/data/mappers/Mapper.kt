package francisco.simon.myfinance.data.mappers

import francisco.simon.myfinance.data.dto.AccountByIdDto
import francisco.simon.myfinance.data.dto.AccountDto
import francisco.simon.myfinance.data.dto.AccountUpdateRequestDto
import francisco.simon.myfinance.data.dto.CategoryDto
import francisco.simon.myfinance.data.dto.StateItemDto
import francisco.simon.myfinance.data.dto.TransactionDto
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.entity.StateItem
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.AccountUpdateRequestModel

/**
 * Mappers from Data to Domain and vice-versa
 * @author Simon Francisco
 */


fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome,
    )
}

fun List<CategoryDto>.toCategoryList(): List<Category> {
    return this.map {
        it.toCategory()
    }
}


fun AccountDto.toAccount(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        updatedAt = updatedAt,
        createdAt = createdAt,
    )
}

fun AccountByIdDto.toAccount(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        incomeStats = incomeStats.toListStateItem(),
        expenseStats = expenseStats.toListStateItem(),
    )
}

fun AccountUpdateRequestModel.toAccountUpdateRequestDto(): AccountUpdateRequestDto {
    return AccountUpdateRequestDto(
        name = name,
        balance = balance,
        currency = currency
    )
}

fun StateItemDto.toStateItem(): StateItem {
    return StateItem(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount,
    )
}

fun List<StateItemDto>.toListStateItem(): List<StateItem> {
    return this.map {
        it.toStateItem()
    }
}

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        id = id,
        account = account.toAccount(),
        category = category.toCategory(),
        amount = amount,
        comment = comment,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}