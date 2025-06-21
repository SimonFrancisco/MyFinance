package francisco.simon.myfinance.data.mappers

import francisco.simon.myfinance.data.dto.AccountDto
import francisco.simon.myfinance.data.dto.CategoryDto
import francisco.simon.myfinance.data.dto.TransactionDto
import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.entity.Transaction


fun Category.toCategoryDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}


fun Account.toAccountDto(): AccountDto {
    return AccountDto(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun AccountDto.toAccount(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun Transaction.toTransactionDto(): TransactionDto {
    return TransactionDto(
        id = id,
        account = account.toAccountDto(),
        category = category.toCategoryDto(),
        amount = amount,
        comment = comment,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
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
        updatedAt = updatedAt
    )
}