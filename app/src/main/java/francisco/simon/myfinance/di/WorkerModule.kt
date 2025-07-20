package francisco.simon.myfinance.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.myfinance.workers.factory.ChildWorkerFactory
import francisco.simon.myfinance.workers.SyncWorker

@Module
interface WorkerModule {

    @[Binds IntoMap WorkerKey(SyncWorker::class)]
    fun bindSyncWorkerFactory(worker: SyncWorker.Factory): ChildWorkerFactory
}