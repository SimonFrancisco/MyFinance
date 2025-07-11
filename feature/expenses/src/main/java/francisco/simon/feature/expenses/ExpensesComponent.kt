package francisco.simon.feature.expenses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import francisco.simon.feature.expenses.di.ExpensesComponent
import francisco.simon.feature.expenses.di.ExpensesDependenciesProvider
import francisco.simon.feature.expenses.di.DaggerExpensesComponent

// TODO make component live as longs as the feature lives

@Composable
internal fun expensesComponent(): ExpensesComponent {
    val context = LocalContext.current.applicationContext
    val expensesDependencies = remember {
        (context as ExpensesDependenciesProvider).getExpensesDependencies()
    }
    val component = remember(expensesDependencies) {
        DaggerExpensesComponent.builder().expensesDependencies(expensesDependencies).build()
    }
    return component
}