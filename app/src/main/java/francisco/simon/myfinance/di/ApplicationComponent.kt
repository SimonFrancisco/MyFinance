package francisco.simon.myfinance.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import francisco.simon.core.domain.preferences.AboutPreferences
import francisco.simon.core.domain.preferences.ColorSchemePreferences
import francisco.simon.core.domain.preferences.LanguagePreferences
import francisco.simon.core.domain.preferences.SyncPreferences
import francisco.simon.core.domain.preferences.ThemeModePreferences
import francisco.simon.core.domain.repository.AccountRepository
import francisco.simon.core.domain.repository.CategoryRepository
import francisco.simon.core.domain.repository.TransactionRepository
import francisco.simon.feature.account.di.AccountDependencies
import francisco.simon.feature.category.di.CategoryDependencies
import francisco.simon.feature.expenses.di.ExpensesDependencies
import francisco.simon.feature.income.di.IncomeDependencies
import francisco.simon.feature.settings.di.SettingsDependencies
import francisco.simon.myfinance.App
import francisco.simon.myfinance.di.factory.ViewModelFactory
import francisco.simon.myfinance.di.modules.LocalDataModule
import francisco.simon.myfinance.di.modules.PreferencesModule
import francisco.simon.myfinance.di.modules.RemoteDataModule
import francisco.simon.myfinance.di.modules.ViewModelModule
import francisco.simon.myfinance.di.modules.WorkerModule

@ApplicationScope
@Component(
    modules = [
        RemoteDataModule::class,
        LocalDataModule::class,
        WorkerModule::class,
        ViewModelModule::class,
        PreferencesModule::class]
)
internal interface ApplicationComponent :
    CategoryDependencies,
    AccountDependencies,
    IncomeDependencies,
    ExpensesDependencies,
    SettingsDependencies {

    override fun getTransactionsRepository(): TransactionRepository

    override fun getAccountRepository(): AccountRepository

    override fun getCategoryRepository(): CategoryRepository

    override fun getSyncPreferences(): SyncPreferences

    override fun getColorSchemePreferences(): ColorSchemePreferences

    override fun getThemeModePreferences(): ThemeModePreferences

    override fun getAboutPreferences(): AboutPreferences

    override fun languagePreferences(): LanguagePreferences

    fun inject(application: App)

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}