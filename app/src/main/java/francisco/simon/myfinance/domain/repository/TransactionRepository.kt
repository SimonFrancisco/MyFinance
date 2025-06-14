package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getTransactions(): Flow<List<Transaction>>

}