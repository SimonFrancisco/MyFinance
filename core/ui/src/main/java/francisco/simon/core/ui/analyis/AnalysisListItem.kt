package francisco.simon.core.ui.analyis

import androidx.compose.foundation.background
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
import francisco.simon.core.domain.model.CategoryStatsModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.utils.toCurrencySymbol

@Composable
fun AnalysisListItem(
    categoryStatsModel: CategoryStatsModel,
) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp),
        headlineContent = {
            HeadingContent(categoryStatsModel)
        },
        leadingContent = {
            LeadingContent(categoryStatsModel)
        },
        trailingContent = {
            TrailingContent(categoryStatsModel)
        }
    )
}

@Composable
private fun HeadingContent(categoryStatsModel: CategoryStatsModel) {
    Text(
        text = categoryStatsModel.name,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun LeadingContent(categoryStatsModel: CategoryStatsModel) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center

    ) {
        Text(categoryStatsModel.emoji)
    }
    Spacer(Modifier.width(16.dp))
}

@Composable
private fun TrailingContent(categoryStatsModel: CategoryStatsModel) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "${"%.1f".format(categoryStatsModel.percent)} %",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "${categoryStatsModel.amount} ${categoryStatsModel.currency.toCurrencySymbol()}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp),
        )

    }
    Icon(
        painter = painterResource(R.drawable.ic_arrow_head),
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
    )
}