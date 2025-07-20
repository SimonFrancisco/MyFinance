package francisco.simon.core.data.local.transactions.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import francisco.simon.core.data.local.transactions.model.TransactionDbModel

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions WHERE isDeleted=0 AND accountId=:accountId AND transactionDate BETWEEN :startDate and :endDate")
    suspend fun getTransactions(
        accountId: Int,
        startDate: String,
        endDate: String,
    ): List<TransactionDbModel>

    @Transaction
    @Query(
        """
            UPDATE transactions 
            SET accountName =:newAccountName,
                balance=:newAccountBalance,
                currency =:newAccountCurrency
            WHERE accountId =:accountId
        """
    )
    suspend fun updateTransactionsForAccountUpdate(
        accountId: Int,
        newAccountName: String,
        newAccountBalance: String,
        newAccountCurrency: String
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionDbModel: TransactionDbModel)

    @Update
    suspend fun updateTransaction(transactionDbModel: TransactionDbModel)

    @Query("SELECT * FROM transactions WHERE transactionId=:transactionId LIMIT 1")
    suspend fun getTransactionById(transactionId: Int): TransactionDbModel

    @Query("DELETE from transactions WHERE transactionId=:id")
    suspend fun deleteTransactionById(id: Int)

    @Query("SELECT * FROM transactions WHERE isAdded=0")
    suspend fun getNotAddedTransactions(): List<TransactionDbModel>

    @Query("SELECT * FROM transactions WHERE isEdited=0")
    suspend fun getNotEditedTransaction(): List<TransactionDbModel>

    @Query("SELECT * FROM transactions WHERE isDeleted=1")
    suspend fun getNotDeletedTransaction(): List<TransactionDbModel>
}