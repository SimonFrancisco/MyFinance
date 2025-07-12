package francisco.simon.feature.account.di

import francisco.simon.core.domain.repository.AccountRepository

interface AccountDependencies {
    fun getAccountRepository(): AccountRepository
}