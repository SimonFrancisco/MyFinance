@file:OptIn(ExperimentalMaterial3Api::class)

package francisco.simon.core.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import francisco.simon.core.domain.entity.Category
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.theme.Red
import francisco.simon.core.ui.transactions.addTransaction.AddTransactionBaseViewModel.AddTransaction

@Composable
internal fun CategoryBottomSheet(
    showSheet: MutableState<Boolean>,
    updateModelState: MutableState<AddTransaction>,
    categories: List<Category>
) {
    if (showSheet.value) {
        CategoryPickerBottomSheet(
            categories = categories,
            onCategorySelected = {
                updateModelState.value = updateModelState.value.copy(category = it)
            }, onDismissRequest = { showSheet.value = false }
        )
    }
}

@Composable
private fun CategoryPickerBottomSheet(
    onCategorySelected: (Category) -> Unit,
    onDismissRequest: () -> Unit,
    categories: List<Category>
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        tonalElevation = 2.dp,
        containerColor = francisco.simon.core.ui.theme.GreyBottomSheet
    ) {
        categories.forEach { category ->
            CustomListItem(
                modifier = Modifier
                    .height(
                        72.dp
                    )
                    .clickable {
                        onCategorySelected(category)
                        onDismissRequest()
                    },
                leadingContent = {
                    Box(
                        Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center

                    ) {
                        Text(category.emoji)
                    }
                    Spacer(Modifier.width(16.dp))
                }, headlineContent = {
                    Text(
                        text = category.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            )
            HorizontalDivider()
        }
        CustomListItem(
            modifier = Modifier
                .height(
                    72.dp
                )
                .background(
                    Red
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
