package francisco.simon.myfinance

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import francisco.simon.feature.account.di.AccountDependencies
import francisco.simon.feature.account.di.AccountDependenciesProvider
import francisco.simon.feature.category.di.CategoryDependencies
import francisco.simon.feature.category.di.CategoryDependenciesProvider
import francisco.simon.feature.expenses.di.ExpensesDependencies
import francisco.simon.feature.expenses.di.ExpensesDependenciesProvider
import francisco.simon.feature.income.di.IncomeDependencies
import francisco.simon.feature.income.di.IncomeDependenciesProvider
import francisco.simon.feature.settings.di.SettingsDependencies
import francisco.simon.feature.settings.di.SettingsDependenciesProvider
import francisco.simon.myfinance.di.ApplicationComponent
import francisco.simon.myfinance.di.DaggerApplicationComponent
import francisco.simon.myfinance.workers.syn_worker.SyncWorker
import francisco.simon.myfinance.workers.factory.WorkerFactory
import javax.inject.Inject

internal class App : Application(),
    CategoryDependenciesProvider,
    AccountDependenciesProvider,
    IncomeDependenciesProvider,
    ExpensesDependenciesProvider,
    SettingsDependenciesProvider,
    Configuration.Provider {

    @Inject
    lateinit var workerFactory: WorkerFactory

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        component.inject(this)
        startSyncWorker()
        super.onCreate()
    }

    private fun startSyncWorker() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            uniqueWorkName = SyncWorker.WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = SyncWorker.startUpSyncWork()
        )
    }

    override fun getCategoryDependencies(): CategoryDependencies {
        return component
    }

    override fun getAccountDependencies(): AccountDependencies {
        return component
    }

    override fun getIncomeDependencies(): IncomeDependencies {
        return component
    }

    override fun getExpensesDependencies(): ExpensesDependencies {
        return component
    }

    override fun getSettingsDependencies(): SettingsDependencies {
        return component
    }
}

@Composable
internal fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as App).component
}