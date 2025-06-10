package francisco.simon.myfinance.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import francisco.simon.myfinance.data.FinanceRepositoryImpl
import francisco.simon.myfinance.domain.repository.FinanceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface FinanceModule {

    @Singleton
    @Binds
    fun bindFinanceRepository(impl: FinanceRepositoryImpl): FinanceRepository
}