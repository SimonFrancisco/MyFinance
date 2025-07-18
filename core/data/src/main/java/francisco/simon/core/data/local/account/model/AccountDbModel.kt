package francisco.simon.core.data.local.account.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts"
)
data class AccountDbModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val isSynchronized: Boolean = false
)
