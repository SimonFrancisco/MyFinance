@file:OptIn(ExperimentalMaterial3Api::class)
package francisco.simon.myfinance.ui.features.account.screens.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import francisco.simon.core.domain.model.AccountUpdateRequestModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.ui.features.account.screens.edit.utils.Currency

@Composable
private fun CurrencyPickerBottomSheet(
    onCurrencySelected: (Currency) -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        tonalElevation = 2.dp,
        containerColor = francisco.simon.core.ui.theme.GreyBottomSheet
    ) {
        Currency.entries.forEach { currency ->
            francisco.simon.core.ui.components.CustomListItem(
                modifier = Modifier
                    .height(
                        72.dp
                    )
                    .clickable {
                        onCurrencySelected(currency)
                    },
                leadingContent = {
                    Icon(
                        painter = painterResource(currency.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                }, headlineContent = {
                    Text(
                        text = "${stringResource(currency.displayNameRes)} ${currency.symbol}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            )
            HorizontalDivider()
        }
        francisco.simon.core.ui.components.CustomListItem(
            modifier = Modifier
                .height(
                    72.dp
                )
                .background(
                    francisco.simon.core.ui.theme.Red
                )
                .clickable {
                    onDismissRequest()
                },
            leadingContent = {
                Icon(
                    painter = painterResource(R.drawable.ic_sheet_cancel),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(Modifier.width(16.dp))
            }, headlineContent = {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        )
    }
}

@Composable
fun BottomSheet(
    showSheet: MutableState<Boolean>,
    updateModelState: MutableState<AccountUpdateRequestModel?>,
) {
    if (showSheet.value) {
        CurrencyPickerBottomSheet(
            onCurrencySelected = {
                updateModelState.value = updateModelState.value?.copy(currency = it.name)
            }, onDismissRequest = { showSheet.value = false }
        )
    }
}