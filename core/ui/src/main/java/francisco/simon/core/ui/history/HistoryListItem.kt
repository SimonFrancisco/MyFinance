package francisco.simon.core.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import francisco.simon.core.domain.entity.Transaction
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.core.ui.utils.toDateAndTime

/**
 * Settings for every item history list.
 *
 * @see TrailingContent
 * @see LeadingContent
 * @see HeadingContent
 *
 * @author Simon Francisco
 */
@Composable
fun HistoryListItem(
    transaction: Transaction,
    onTransactionClicked: (Int) -> Unit
) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable {
                onTransactionClicked(transaction.id)
            },
        headlineContent = {
            HeadingContent(transaction)
        },
        leadingContent = {
            LeadingContent(transaction)
        },
        trailingContent = {
            TrailingContent(transaction)
        }
    )
}

@Composable
private fun TrailingContent(transaction: Transaction) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "${transaction.amount} ${transaction.account.currency.toCurrencySymbol()}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp),
        )
        Text(
            text = transaction.transactionDate.toDateAndTime(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
    Icon(
        painter = painterResource(R.drawable.ic_arrow_head),
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
    )
}

@Composable
private fun LeadingContent(transaction: Transaction) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center

    ) {
        Text(transaction.category.emoji)
    }
    Spacer(Modifier.width(16.dp))
}

@Composable
private fun HeadingContent(transaction: Transaction) {
    Text(
        text = transaction.category.name,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
    if (!transaction.comment.isNullOrEmpty()) {
        Text(
            text = transaction.comment ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}