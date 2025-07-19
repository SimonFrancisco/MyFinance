package francisco.simon.core.data.local.transactions.mappers

import francisco.simon.core.data.local.account.model.AccountDbModel
import francisco.simon.core.data.local.category.model.CategoryDbModel
import francisco.simon.core.data.local.transactions.model.AccountTransaction
import francisco.simon.core.data.local.transactions.model.CategoryTransaction
import francisco.simon.core.data.local.transactions.model.TransactionDbModel
import francisco.simon.core.data.network.dto.CategoryDto
import francisco.simon.core.data.network.dto.account.AccountDto
import francisco.simon.core.data.network.dto.transactions.TransactionDto
import francisco.simon.core.data.network.dto.transactions.TransactionResponseDto
import francisco.simon.core.domain.entity.Account
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.domain.model.TransactionResponseModel
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


fun String.toTransactionDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(this, dateFormat)
    return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toString()
}

fun TransactionDbModel.toTransaction(): Transaction {
    return Transaction(
        id = transactionId,
        account = accountTransaction.toAccount(),
        category = categoryTransaction.toCategory(),
        amount = amount,
        comment = comment,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun List<TransactionDbModel>.toListTransaction(): List<Transaction> {
    return this.map { it.toTransaction() }
}

fun AccountTransaction.toAccount(): Account {
    return Account(
        accountId = accountId,
        name = accountName,
        balance = balance,
        currency = currency
    )
}

fun TransactionDbModel.toTransactionResponseModel():TransactionResponseModel{
    return TransactionResponseModel(
        id = transactionId,
        accountId = accountTransaction.accountId,
        categoryId = categoryTransaction.categoryId,
        amount = amount,
        comment = comment,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun AccountDbModel.toAccountTransaction(): AccountTransaction {
    return AccountTransaction(
        accountId = id,
        accountName = name,
        balance = balance,
        currency = currency
    )
}


fun CategoryDbModel.toCategoryTransaction(): CategoryTransaction {
    return CategoryTransaction(
        categoryId = id,
        categoryName = name,
        isIncome = isIncome,
        emoji = emoji
    )
}


fun CategoryTransaction.toCategory(): Category {
    return Category(
        id = categoryId,
        name = categoryName,
        isIncome = isIncome,
        emoji = emoji
    )
}

fun CategoryDto.toCategoryTransaction(): CategoryTransaction {
    return CategoryTransaction(
        categoryId = id,
        categoryName = name,
        isIncome = isIncome,
        emoji = emoji
    )
}

fun AccountDto.toAccountTransaction(): AccountTransaction {
    return AccountTransaction(
        accountId = id,
        accountName = name,
        balance = balance,
        currency = currency
    )
}

fun TransactionDto.toAddDB(isAdded: Boolean): TransactionDbModel {
    return TransactionDbModel(
        transactionId = id,
        amount = amount,
        categoryTransaction = category.toCategoryTransaction(),
        accountTransaction = account.toAccountTransaction(),
        isAdded = isAdded,
        comment = comment,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun TransactionDto.toEditDB(isEdited: Boolean): TransactionDbModel {
    return TransactionDbModel(
        transactionId = id,
        amount = amount,
        categoryTransaction = category.toCategoryTransaction(),
        accountTransaction = account.toAccountTransaction(),
        isEdited = isEdited,
        comment = comment,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}