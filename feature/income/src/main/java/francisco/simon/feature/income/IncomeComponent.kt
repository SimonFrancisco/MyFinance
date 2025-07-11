package francisco.simon.feature.income

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import francisco.simon.feature.income.di.IncomeComponent
import francisco.simon.feature.income.di.IncomeDependenciesProvider
import francisco.simon.feature.income.di.DaggerIncomeComponent

// TODO make component live as longs as the feature lives

@Composable
internal fun incomeComponent(): IncomeComponent {
    val context = LocalContext.current.applicationContext
    val incomeDependencies = remember {
        (context as IncomeDependenciesProvider).getIncomeDependencies()
    }
    val component = remember(incomeDependencies) {
        DaggerIncomeComponent.builder().incomeDependencies(incomeDependencies).build()
    }
    return component
}