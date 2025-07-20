package francisco.simon.core.data.local.transactions.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "transactions",
    indices = [Index(value = ["accountId"])])
data class TransactionDbModel(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,
    @Embedded
    val accountTransaction: AccountTransaction,
    @Embedded
    val categoryTransaction: CategoryTransaction,
    val amount: String,
    val comment: String?,
    val transactionDate: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean = false,
    val isAdded: Boolean = false,
    val isEdited: Boolean = false
)