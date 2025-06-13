package francisco.simon.myfinance.ui.features.icome.screens.income

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.ui.features.icome.model.Income

@Composable
fun IncomeScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.income_app_top_bar,
                actionButton = ActionButton(R.drawable.ic_history) {
                }
            )
        )
    }
    val viewModel: IncomeScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    IncomeScreenContent(currentState)
}

@Composable
fun IncomeScreenContent(
    state: IncomeScreenState
) {
    when (state) {
        is IncomeScreenState.Error -> {

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
fun IncomeList(
    income: List<Income>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val sum = income.sumOf {
            it.amount.toLong()
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
                    text = "$sum ${income.first().currency.toCurrencySymbol()}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
        )
        HorizontalDivider()
        LazyColumn {
            items(income, key = { it.transactionId }) { income ->
                CustomListItem(
                    modifier = Modifier
                        .height(72.dp)
                        .clickable {

                        },
                    headlineContent = {
                        Text(
                            text = income.name,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        if (!income.comment.isNullOrEmpty()) {
                            Text(
                                text = income.comment,
                                style = MaterialTheme.typography.bodyLarge,

                                textAlign = TextAlign.Center
                            )
                        }

                    },
                    trailingContent = {
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
                )
                HorizontalDivider()
            }
        }
    }

}