package francisco.simon.myfinance.core.ui.history

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.core.mapper.toDateAndTime
import francisco.simon.myfinance.domain.entity.Transaction

@Composable
fun HistoryListItem(transaction: Transaction) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable {

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
            text = transaction.updatedAt.toDateAndTime(),
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
        color = MaterialTheme.colorScheme.onSurface,
    )
    if (!transaction.comment.isNullOrEmpty()) {
        Text(
            text = transaction.comment,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}