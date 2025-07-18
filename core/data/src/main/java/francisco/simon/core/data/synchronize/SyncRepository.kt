package francisco.simon.core.data.synchronize

interface SyncRepository {
    suspend fun synchronize()
}