package francisco.simon.feature.expenses.ui.screens.expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.theme.Green
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.feature.expenses.expensesComponent
import francisco.simon.feature.expenses.ui.model.ExpenseUI

/**
 * Expense Screen, separate concerns to avoid unnecessary recompositions and
 * keep code logic short
 * @author Simon Francisco
 */
internal var onGoToAddExpenseScreenGlobal: (() -> Unit)? = null

@Composable
internal fun ExpenseScreen(
    appBarState: MutableState<AppBarState>,
    onGoToHistoryScreen: () -> Unit,
    onGoToAddExpenseScreen: () -> Unit,
    onGoToEditExpenseScreen: (transactionId: Int) -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.expense_app_top_bar,
        actionButton = ActionButton(R.drawable.ic_history) {
            onGoToHistoryScreen()
        }
    )
    onGoToAddExpenseScreenGlobal = onGoToAddExpenseScreen
    val component = expensesComponent()
    val viewModel: ExpenseScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    LaunchedEffect(Unit) {
        viewModel.loadExpenses()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    ExpenseScreenContent(currentState, viewModel, onGoToEditExpenseScreen)

}

@Composable
private fun ExpenseScreenContent(
    state: ExpenseScreenState,
    viewModel: ExpenseScreenViewModel,
    onTransactionClicked: (Int) -> Unit
) {
    when (state) {
        is ExpenseScreenState.Error -> {
            francisco.simon.core.ui.components.RetryCall(
                errorRes = state.errorMessageRes,
                onClick = {
                    viewModel.retry()
                },
            )
        }

        is ExpenseScreenState.Loading -> {
            francisco.simon.core.ui.components.FullScreenLoading()
        }

        is ExpenseScreenState.Success -> {
            ExpenseList(expenses = state.expenses, onTransactionClicked = onTransactionClicked)
        }
    }
}

@Composable
private fun ExpenseList(
    expenses: List<ExpenseUI>,
    onTransactionClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ExpenseSumItem(expenses)
        HorizontalDivider()
        LazyColumn {
            items(expenses, key = { it.transactionId }) { expense ->
                CustomListItem(
                    modifier = Modifier
                        .height(70.dp)
                        .clickable {
                            onTransactionClicked(expense.transactionId)
                        },
                    headlineContent = {
                        ExpenseHeadingContent(expense)
                    },
                    leadingContent = {
                        ExpenseLeadingContent(expense)
                        Spacer(Modifier.width(16.dp))
                    },
                    trailingContent = {
                        ExpenseTrailingContent(expense)
                    }
                )
                HorizontalDivider()
            }
        }
    }

}

@Composable
private fun ExpenseTrailingContent(expenseUI: ExpenseUI) {
    Text(
        text = "${expenseUI.amount} ${expenseUI.currency.toCurrencySymbol()}",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(end = 8.dp)
    )
    Icon(
        painter = painterResource(R.drawable.ic_arrow_head),
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
    )
}

@Composable
private fun ExpenseLeadingContent(expenseUI: ExpenseUI) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center

    ) {
        Text(expenseUI.emoji)
    }
}

@Composable
private fun ExpenseHeadingContent(expenseUI: ExpenseUI) {
    Text(
        text = expenseUI.name,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    if (!expenseUI.comment.isNullOrEmpty()) {
        Text(
            text = expenseUI.comment,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ExpenseSumItem(expenses: List<ExpenseUI>) {
    val sum = expenses.sumOf {
        it.amount
    }
    CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        headlineContent = {
            Text(
                text = stringResource(R.string.all_money),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = "$sum ${expenses.firstOrNull()?.currency?.toCurrencySymbol() ?: ""}",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
    )
}

@Composable
fun ExpenseFloatingButton() {
    FloatingActionButton(
        containerColor = Green,
        shape = CircleShape,
        modifier = Modifier
            .size(56.dp),
        onClick = {
            onGoToAddExpenseScreenGlobal?.invoke()
        }
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
            contentDescription = null
        )
    }

}