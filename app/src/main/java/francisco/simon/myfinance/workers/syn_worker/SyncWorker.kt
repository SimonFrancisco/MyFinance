package francisco.simon.myfinance.workers.syn_worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import francisco.simon.core.domain.preferences.SyncPreferences
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.myfinance.workers.factory.ChildWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val syncPreferences: SyncPreferences
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            transactionRepository.synchronize()
            accountRepository.synchronize()
            syncPreferences.saveLastSyncTime(System.currentTimeMillis())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }

    }

    companion object {
        const val WORK_NAME = "SyncWorker"
        private const val SYNC_INTERVAL = 4L
        private const val BACKOFF_DELAY = 30L

        fun startUpSyncWork(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<SyncWorker>(
                repeatInterval = SYNC_INTERVAL, repeatIntervalTimeUnit = TimeUnit.HOURS
            ).setConstraints(
                constraints = makeConstraints()
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = BACKOFF_DELAY, timeUnit = TimeUnit.SECONDS
            ).build()
        }

        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED).build()
        }

    }

    class Factory @Inject constructor(
        private val transactionRepository: TransactionRepository,
        private val accountRepository: AccountRepository,
        private val syncPreferences: SyncPreferences
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return SyncWorker(
                context = context,
                workerParameters = workerParameters,
                transactionRepository = transactionRepository,
                accountRepository = accountRepository,
                syncPreferences = syncPreferences
            )
        }
    }
}