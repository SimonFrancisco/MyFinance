package francisco.simon.myfinance

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import francisco.simon.feature.account.di.AccountDependencies
import francisco.simon.feature.account.di.AccountDependenciesProvider
import francisco.simon.feature.category.di.CategoryDependencies
import francisco.simon.feature.category.di.CategoryDependenciesProvider
import francisco.simon.feature.expenses.di.ExpensesDependencies
import francisco.simon.feature.expenses.di.ExpensesDependenciesProvider
import francisco.simon.feature.income.di.IncomeDependencies
import francisco.simon.feature.income.di.IncomeDependenciesProvider
import francisco.simon.myfinance.di.ApplicationComponent
import francisco.simon.myfinance.di.DaggerApplicationComponent

internal class App : Application(),
    CategoryDependenciesProvider,
    AccountDependenciesProvider,
    IncomeDependenciesProvider,
    ExpensesDependenciesProvider {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
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
}

@Composable
internal fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as App).component
}