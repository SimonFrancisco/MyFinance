package francisco.simon.feature.income.ui.screens.income

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.RetryCall
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.feature.income.incomeComponent
import francisco.simon.feature.income.ui.model.IncomeUI

/**
 * Income Screen, separate concerns to avoid unnecessary recompositions and
 * keep code logic short
 * @author Simon Francisco
 */
internal var onGoToAddIncomeScreenGlobal: (() -> Unit)? = null

@Composable
internal fun IncomeScreen(
    appBarState: MutableState<AppBarState>,
    onGoToIncomeHistoryScreen: () -> Unit,
    onGoToAddIncomeScreen: () -> Unit,
    onGoToEditIncomeScreen: (transactionId: Int) -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.income_app_top_bar,
        actionButton = ActionButton(R.drawable.ic_history) {
            onGoToIncomeHistoryScreen()
        }

    )

    onGoToAddIncomeScreenGlobal = onGoToAddIncomeScreen
    val component = incomeComponent()
    val viewModel: IncomeScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    LaunchedEffect(Unit) {
        viewModel.loadIncome()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    IncomeScreenContent(currentState, viewModel, onGoToEditIncomeScreen)

}


@Composable
private fun IncomeScreenContent(
    state: IncomeScreenState,
    viewModel: IncomeScreenViewModel,
    onTransactionClicked: (Int) -> Unit
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
            IncomeList(
                incomeUI = state.incomeUI,
                onTransactionClicked = onTransactionClicked
            )
        }
    }
}

@Composable
private fun IncomeList(
    incomeUI: List<IncomeUI>,
    onTransactionClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IncomeSumItem(incomeUI)
        HorizontalDivider()
        LazyColumn {
            items(incomeUI, key = { it.transactionId }) { income ->
                CustomListItem(
                    modifier = Modifier
                        .height(72.dp)
                        .clickable {
                            onTransactionClicked(income.transactionId)
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
private fun IncomeTrailingContent(incomeUI: IncomeUI) {
    Text(
        text = "${incomeUI.amount} ${incomeUI.currency.toCurrencySymbol()}",
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
private fun IncomeLeadingContent(incomeUI: IncomeUI) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center

    ) {
        Text(incomeUI.emoji)
    }
}

@Composable
private fun IncomeHeadingContent(incomeUI: IncomeUI) {
    Text(
        text = incomeUI.name,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    if (!incomeUI.comment.isNullOrEmpty()) {
        Text(
            text = incomeUI.comment,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun IncomeSumItem(incomeUI: List<IncomeUI>) {
    val sum = incomeUI.sumOf {
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
                text = "$sum ${incomeUI.firstOrNull()?.currency?.toCurrencySymbol() ?: ""}",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
    )
}

@Composable
fun IncomeFloatingButton() {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape,
        modifier = Modifier
            .size(56.dp),
        onClick = {
            onGoToAddIncomeScreenGlobal?.invoke()
        }
    ) {
        Image(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }

}