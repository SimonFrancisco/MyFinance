package francisco.simon.myfinance.ui.features.expense.screens.expense

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.RetryCall
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.core.ui.utils.UpdateWhenGoingBack
import francisco.simon.myfinance.getApplicationComponent
import francisco.simon.myfinance.navigation.ExpenseGraph.ExpensesHistoryRoute
import francisco.simon.myfinance.navigation.LocalNavController
import francisco.simon.myfinance.ui.features.expense.model.ExpenseUI

/**
 * Expense Screen, separate concerns to avoid unnecessary recompositions and
 * keep code logic short
 * @author Simon Francisco
 */
@Composable
fun ExpenseScreen(appBarState: MutableState<AppBarState>) {
    val navController = LocalNavController.current
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.expense_app_top_bar,
        actionButton = ActionButton(R.drawable.ic_history) {
            navController.navigate(ExpensesHistoryRoute)
        }
    )
    val component = getApplicationComponent()
    val viewModel: ExpenseScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    ExpenseScreenContent(currentState, viewModel)
    UpdateWhenGoingBack {
        viewModel.retry()
    }
}

@Composable
private fun ExpenseScreenContent(
    state: ExpenseScreenState,
    viewModel: ExpenseScreenViewModel,
) {
    when (state) {
        is ExpenseScreenState.Error -> {
            RetryCall(
                errorRes = state.errorMessageRes,
                onClick = {
                    viewModel.retry()
                },
            )
        }

        is ExpenseScreenState.Loading -> {
            FullScreenLoading()
        }

        is ExpenseScreenState.Success -> {
            ExpenseList(state.expens)
        }
    }
}

@Composable
private fun ExpenseList(
    expens: List<ExpenseUI>,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ExpenseSumItem(expens)
        HorizontalDivider()
        LazyColumn {
            items(expens, key = { it.transactionId }) { expense ->
                CustomListItem(
                    modifier = Modifier
                        .height(70.dp)
                        .clickable {},
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
private fun ExpenseSumItem(expens: List<ExpenseUI>) {
    val sum = expens.sumOf {
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
                text = "$sum ${expens.firstOrNull()?.currency?.toCurrencySymbol() ?: ""}",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
    )
}
