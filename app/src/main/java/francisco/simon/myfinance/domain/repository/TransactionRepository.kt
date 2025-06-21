package francisco.simon.myfinance.domain.repository

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.model.TransactionModel
import francisco.simon.myfinance.domain.utils.NetworkResult

interface TransactionRepository {

    suspend fun getTransactions(transactionModel: TransactionModel): NetworkResult<List<Transaction>>

    suspend fun getAccount(): NetworkResult<Account>

}