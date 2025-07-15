package francisco.simon.feature.expenses.di

import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.repository.CategoryRepository
import francisco.simon.core.domain.repository.TransactionRepository

interface ExpensesDependencies {
    fun getAccountRepository(): AccountRepository
    fun getTransactionsRepository():TransactionRepository
    fun getCategoryRepository(): CategoryRepository
}