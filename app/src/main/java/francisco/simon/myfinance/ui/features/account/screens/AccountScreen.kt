package francisco.simon.myfinance.ui.features.account.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.topBar.ActionButton
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.ui.features.account.model.AccountUI

@Composable
fun AccountScreen(appBarConfig: (AppBarState) -> Unit) {

    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.account_app_top_bar,
                actionButton = ActionButton(
                    icon = R.drawable.ic_edit
                ) {

                }
            )
        )
    }
    val viewModel: AccountViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    AccountScreenContent(currentState)
}

@Composable
fun AccountScreenContent(
    state: AccountScreenState
) {
    when (state) {
        is AccountScreenState.Error -> {

        }

        is AccountScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is AccountScreenState.Success -> {
            AccountScreenList(state.account)
        }
    }
}

@Composable
fun AccountScreenList(
    accountUI: AccountUI
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomListItem(
            modifier = Modifier
                .height(57.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {

                },
            headlineContent = {
                Text(
                    text = accountUI.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
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
            },
            leadingContent = {
                Image(
                    imageVector = ImageVector.vectorResource(accountUI.emojiRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White)

                )
                Spacer(Modifier.width(16.dp))
            }
        )
        HorizontalDivider()
        CustomListItem(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {

                },
            headlineContent = {
                Text(
                    text = stringResource(R.string.currency),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
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
        )

    }
}
