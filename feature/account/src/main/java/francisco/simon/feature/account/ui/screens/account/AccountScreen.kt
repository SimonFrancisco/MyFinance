package francisco.simon.feature.account.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.RetryCall
import francisco.simon.core.ui.components.topBar.ActionButton
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.feature.account.accountComponent
import francisco.simon.feature.account.ui.model.AccountUI

/**
 * Account Screen, separate concerns to avoid unnecessary recompositions and
 * keep code logic short
 * @author Simon Francisco
 */
private var ACCOUNT_ID = -1

@Composable
internal fun AccountScreen(
    appBarState: MutableState<AppBarState>,
    onOpenEditScreen: (accountId: Int) -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.account_app_top_bar,
        actionButton = ActionButton(
            icon = R.drawable.ic_edit
        ) {
            if (ACCOUNT_ID != -1) {
                onOpenEditScreen(ACCOUNT_ID)
            }
        }
    )
    val component = accountComponent()
    val viewModel: AccountViewModel = viewModel(factory = component.getViewModelFactory())
    LaunchedEffect(Unit) {
        viewModel.loadAccount()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    AccountScreenContent(
        state = currentState,
        viewModel = viewModel
    )


}

@Composable
private fun AccountScreenContent(
    state: AccountScreenState,
    viewModel: AccountViewModel,
) {
    when (state) {
        is AccountScreenState.Error -> {
            RetryCall(
                errorRes = state.errorMessageRes,
                onClick = {
                    viewModel.retry()
                },
            )
        }

        is AccountScreenState.Loading -> {
            francisco.simon.core.ui.components.FullScreenLoading()
        }

        is AccountScreenState.Success -> {
            ACCOUNT_ID = state.account.id
            AccountScreenList(state.account)
        }
    }
}

@Composable
private fun AccountScreenList(
    accountUI: AccountUI
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AccountContent(accountUI)
        HorizontalDivider()
        francisco.simon.core.ui.components.CustomListItem(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            headlineContent = {
                CurrencyHeadingContent()
            },
            trailingContent = {
                CurrencyTrailingContent(accountUI)
            }
        )

    }
}

@Composable
private fun CurrencyTrailingContent(accountUI: AccountUI) {
    Text(
        text = accountUI.currency.toCurrencySymbol(),
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
private fun CurrencyHeadingContent() {
    Text(
        text = stringResource(R.string.currency),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun AccountContent(accountUI: AccountUI) {
    francisco.simon.core.ui.components.CustomListItem(
        modifier = Modifier
            .height(57.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        headlineContent = {
            AccountHeadingContent(accountUI)
        },
        trailingContent = {
            AccountTrailingContent(accountUI)
        },
        leadingContent = {
            AccountLeadingContent(accountUI)
            Spacer(Modifier.width(16.dp))
        }
    )
}

@Composable
private fun AccountHeadingContent(accountUI: AccountUI) {
    Text(
        text = accountUI.name,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun AccountLeadingContent(accountUI: AccountUI) {
    Image(
        imageVector = ImageVector.vectorResource(accountUI.emojiRes),
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.White)

    )
}

@Composable
private fun AccountTrailingContent(accountUI: AccountUI) {
    Text(
        text = "${accountUI.balance} ${accountUI.currency.toCurrencySymbol()}",
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
