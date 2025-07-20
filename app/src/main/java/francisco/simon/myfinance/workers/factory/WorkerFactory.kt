package francisco.simon.myfinance.workers.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import francisco.simon.myfinance.workers.SyncWorker
import javax.inject.Inject
import javax.inject.Provider

class WorkerFactory @Inject constructor(
    private val workerProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.qualifiedName -> {
                val childWorkerFactory = workerProviders[SyncWorker::class.java]?.get()
                return childWorkerFactory?.create(appContext, workerParameters)
            }

            else -> null
        }
    }
}