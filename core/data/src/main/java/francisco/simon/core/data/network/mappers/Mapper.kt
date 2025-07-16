package francisco.simon.core.data.network.mappers

import francisco.simon.core.data.network.dto.account.AccountByIdDto
import francisco.simon.core.data.network.dto.account.AccountDto
import francisco.simon.core.data.network.dto.account.AccountUpdateRequestDto
import francisco.simon.core.data.network.dto.CategoryDto
import francisco.simon.core.data.network.dto.StateItemDto
import francisco.simon.core.data.network.dto.transactions.AddTransactionDto
import francisco.simon.core.data.network.dto.transactions.EditTransactionDto
import francisco.simon.core.data.network.dto.transactions.TransactionDto
import francisco.simon.core.data.network.dto.transactions.TransactionResponseDto
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.entity.StateItem
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.domain.model.AddTransactionModel
import francisco.simon.core.domain.model.EditTransactionModel
import francisco.simon.core.domain.model.TransactionResponseModel

/**
 * Mappers from Data to Domain and vice-versa
 * @author Simon Francisco
 */

internal fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome,
    )
}

internal fun List<CategoryDto>.toCategoryList(): List<Category> {
    return this.map {
        it.toCategory()
    }
}


internal fun AccountDto.toAccount(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        updatedAt = updatedAt,
        createdAt = createdAt,
    )
}

internal fun AccountByIdDto.toAccount(): Account {
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

internal fun AccountUpdateRequestModel.toAccountUpdateRequestDto(): AccountUpdateRequestDto {
    return AccountUpdateRequestDto(
        name = name,
        balance = balance,
        currency = currency
    )
}

internal fun StateItemDto.toStateItem(): StateItem {
    return StateItem(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount,
    )
}

internal fun List<StateItemDto>.toListStateItem(): List<StateItem> {
    return this.map {
        it.toStateItem()
    }
}

internal fun TransactionDto.toTransaction(): Transaction {
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
internal fun TransactionResponseDto.toTransactionResponse():TransactionResponseModel{
    return TransactionResponseModel(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        createdAt = createdAt,
        updatedAt = updatedAt,
        transactionDate = transactionDate,
        comment = comment
    )
}

internal fun AddTransactionModel.toAddTransactionDto(): AddTransactionDto {
    return AddTransactionDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )
}

internal fun EditTransactionModel.toEditTransactionDtoModel(): EditTransactionDto {
    return EditTransactionDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )
}

