package francisco.simon.myfinance.ui.features.icome.screens.income

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.RetryCall
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.navigation.IncomeGraph.IncomeHistoryRoute
import francisco.simon.myfinance.navigation.LocalNavController
import francisco.simon.myfinance.ui.features.icome.model.Income

@Composable
fun IncomeScreen(appBarConfig: (AppBarState) -> Unit) {
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.income_app_top_bar,
                actionButton = ActionButton(R.drawable.ic_history) {
                    navController.navigate(IncomeHistoryRoute)
                }
            )
        )
    }
    val viewModel: IncomeScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    IncomeScreenContent(currentState, viewModel)
}

@Composable
private fun IncomeScreenContent(
    state: IncomeScreenState,
    viewModel: IncomeScreenViewModel
) {
    when (state) {
        is IncomeScreenState.Error -> {
            RetryCall(
                errorRes = state.errorMessageRes,
                onClick = {
                    viewModel.retry()
                },
            )
        }
        is IncomeScreenState.Loading -> {
            FullScreenLoading()
        }
        is IncomeScreenState.Success -> {
            IncomeList(state.income)
        }
    }
}

@Composable
private fun IncomeList(
    income: List<Income>,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IncomeSumItem(income)
        HorizontalDivider()
        LazyColumn {
            items(income, key = { it.transactionId }) { income ->
                CustomListItem(
                    modifier = Modifier
                        .height(72.dp)
                        .clickable {

                        },
                    headlineContent = {
                        IncomeHeadingContent(income)

                    },
                    leadingContent = {
                        IncomeLeadingContent(income)
                        Spacer(Modifier.width(16.dp))
                    },
                    trailingContent = {
                        IncomeTrailingContent(income)
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun IncomeTrailingContent(income: Income) {
    Text(
        text = "${income.amount} ${income.currency.toCurrencySymbol()}",
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
private fun IncomeLeadingContent(income: Income) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center

    ) {
        Text(income.emoji)
    }
}

@Composable
private fun IncomeHeadingContent(income: Income) {
    Text(
        text = income.name,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    if (!income.comment.isNullOrEmpty()) {
        Text(
            text = income.comment,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun IncomeSumItem(income: List<Income>) {
    val sum = income.sumOf {
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
                text = "$sum ${income.firstOrNull()?.currency?.toCurrencySymbol() ?: ""}",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
    )
}