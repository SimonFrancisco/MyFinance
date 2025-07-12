package francisco.simon.feature.income.di

import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.repository.TransactionRepository

interface IncomeDependencies {
    fun getAccountRepository(): AccountRepository
    fun getTransactionsRepository():TransactionRepository
}