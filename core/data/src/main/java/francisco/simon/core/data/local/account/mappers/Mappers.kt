package francisco.simon.core.data.local.account.mappers

import francisco.simon.core.data.local.account.model.AccountDbModel
import francisco.simon.core.data.network.dto.account.AccountByIdDto
import francisco.simon.core.data.network.dto.account.AccountDto
import francisco.simon.core.domain.entity.Account

fun AccountDbModel.toAccount(): Account {
    return Account(
        accountId = id,
        name = name,
        balance = balance,
        currency = currency,
    )
}

fun AccountDto.toDbModel(isSynchronized: Boolean):AccountDbModel{
    return AccountDbModel(
        id = id,
        name = name,
        currency = currency,
        balance = balance,
        isSynchronized = isSynchronized
    )
}
fun AccountByIdDto.toDbModel(isSynchronized: Boolean):AccountDbModel{
    return AccountDbModel(
        id = id,
        name = name,
        currency = currency,
        balance = balance,
        isSynchronized = isSynchronized
    )
}