package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {

    suspend fun getTransactions(): Flow<List<Transaction>>

    suspend fun getAllCategories(): Flow<List<Category>>

}