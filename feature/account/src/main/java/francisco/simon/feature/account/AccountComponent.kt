package francisco.simon.feature.account

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import francisco.simon.feature.account.di.AccountComponent
import francisco.simon.feature.account.di.AccountDependenciesProvider
import francisco.simon.feature.account.di.DaggerAccountComponent

// TODO make component live as longs as the feature lives

@Composable
internal fun accountComponent(): AccountComponent {
    val context = LocalContext.current.applicationContext
    val accountDependencies = remember {
        (context as AccountDependenciesProvider).getAccountDependencies()
    }
    val component = remember(accountDependencies) {
        DaggerAccountComponent.builder().accountDependencies(accountDependencies).build()
    }
    Log.d("AccountComponent", component.toString())
    return component
}