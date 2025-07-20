package francisco.simon.core.domain.preferences


interface SyncPreferences {

    fun saveLastSyncTime(time: Long)

    fun getLastSyncTime(): Long

}