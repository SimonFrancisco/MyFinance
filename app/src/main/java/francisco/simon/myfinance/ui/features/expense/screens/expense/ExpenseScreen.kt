package francisco.simon.myfinance.ui.features.expense.screens.expense

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.ui.features.expense.model.Expense

@Composable
fun ExpenseScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.expense_app_top_bar,
                actionButton = ActionButton(R.drawable.ic_history) {
                }
            )
        )
    }
    val viewModel: ExpenseScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    ExpenseScreenContent(currentState)
}

@Composable
fun ExpenseScreenContent(
    state: ExpenseScreenState
) {
    when (state) {
        is ExpenseScreenState.Error -> {

        }

        is ExpenseScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is ExpenseScreenState.Success -> {
            ExpenseList(state.expenses)
        }
    }
}

@Composable
fun ExpenseList(
    expenses: List<Expense>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val sum = expenses.sumOf {
            it.amount.toLong()
        }
        CustomListItem(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            headlineContent = {
                Text(text = stringResource(R.string.all_money), style = MaterialTheme.typography.bodyLarge)
            },
            trailingContent = {
                Text(
                    text = "$sum ${expenses.first().currency.toCurrencySymbol()}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
        )
        HorizontalDivider()
        LazyColumn {
            items(expenses, key = { it.transactionId }) { expense ->
                CustomListItem(
                    modifier = Modifier
                        .height(70.dp)
                        .clickable {

                        },
                    headlineContent = {
                        Text(
                            text = expense.name,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        if (!expense.comment.isNullOrEmpty()) {
                            Text(
                                text = expense.comment,
                                style = MaterialTheme.typography.bodyLarge,

                                textAlign = TextAlign.Center
                            )
                        }

                    },
                    leadingContent = {
                        Image(
                            imageVector = ImageVector.vectorResource(expense.emojiRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                        )
                        Spacer(Modifier.width(16.dp))
                    },
                    trailingContent = {
                        Text(
                            text = "${expense.amount} ${expense.currency.toCurrencySymbol()}",
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
                )
                HorizontalDivider()
            }
        }
    }

}
