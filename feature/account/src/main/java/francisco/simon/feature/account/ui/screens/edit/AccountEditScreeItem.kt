package francisco.simon.feature.account.ui.screens.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.core.ui.R


@Composable
internal fun AccountEditScreenBalance(
    updateModelState: MutableState<AccountUpdateRequestModel?>
) {
    francisco.simon.core.ui.components.CustomListItem(
        modifier = Modifier.height(
            57.dp
        ),
        headlineContent = {
            Text(
                text = stringResource(R.string.balance),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Box(
                modifier = Modifier
                    .width(100.dp)
            ) {
                BasicTextField(
                    maxLines = 1,
                    value = updateModelState.value?.balance ?: " ",
                    onValueChange = { newValue ->
                        updateModelState.value = updateModelState.value?.copy(balance = newValue)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.End
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                )
            }

        }
    )
}

@Composable
fun AccountEditScreenName(
    updateModelState: MutableState<AccountUpdateRequestModel?>
) {
    francisco.simon.core.ui.components.CustomListItem(
        modifier = Modifier.height(
            57.dp
        ),
        leadingContent = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_money_bag),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))
        },
        headlineContent = {
            Text(
                text = stringResource(R.string.account_name),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Box(
                modifier = Modifier
                    .width(100.dp)
            ) {
                BasicTextField(
                    maxLines = 1,
                    value = updateModelState.value?.name ?: " ",
                    onValueChange = { newValue ->
                        updateModelState.value = updateModelState.value?.copy(name = newValue)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.End
                    ),
                )
            }

        }
    )
}

@Composable
fun AccountEdiScreenCurrency(
    updateModelState: MutableState<AccountUpdateRequestModel?>,
    onClick: () -> Unit
) {
    francisco.simon.core.ui.components.CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .clickable {
                onClick()
            },
        headlineContent = {
            Text(
                text = stringResource(R.string.currency),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = updateModelState.value?.currency?.toCurrencySymbol() ?: "",
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

