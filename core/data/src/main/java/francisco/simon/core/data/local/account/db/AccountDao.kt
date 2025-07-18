package francisco.simon.core.data.local.account.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import francisco.simon.core.data.local.account.model.AccountDbModel

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts LIMIT 1")
    suspend fun getAccount(): AccountDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountDbModel)

    @Update
    suspend fun updateAccount(account: AccountDbModel)

}