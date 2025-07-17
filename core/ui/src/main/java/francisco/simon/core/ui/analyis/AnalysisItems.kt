package francisco.simon.core.ui.analyis

import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import francisco.simon.core.domain.model.CategoryStatsModel
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.CustomListItem
import francisco.simon.core.ui.theme.Green
import francisco.simon.core.ui.utils.toCurrencySymbol
import francisco.simon.core.ui.utils.toDateWritten
import java.math.BigDecimal
import java.time.LocalDate


@Composable
fun AnalysisScreenList(
    categoryStatsModels: List<CategoryStatsModel>,
) {
    val sum = categoryStatsModels.sumOf {
        it.amount.toBigDecimal()
    }
    Column(Modifier.fillMaxSize()) {
        AnalysisSumInfo(sum, categoryStatsModels)
        HorizontalDivider()
        Spacer(Modifier.height(50.dp))
        HorizontalDivider()
        LazyColumn {
            items(categoryStatsModels, key = { it.name }) { categoryStatsModel ->
                AnalysisListItem(categoryStatsModel)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun AnalysisStartInfo(
    startDate: LocalDate,
    showStartPicker: MutableState<Boolean>
) {
    AnalysisInfoItemWithChips(
        leadingTextResId = R.string.start_analysis,
        trailingText = startDate.toDateWritten(),
        onClick = {
            showStartPicker.value = true
        }
    )
}

@Composable
fun AnalysisEndInfo(
    endDate: LocalDate,
    showEndPicker: MutableState<Boolean>
) {
    AnalysisInfoItemWithChips(
        leadingTextResId = R.string.end_analysis,
        trailingText = endDate.toDateWritten(),
        onClick = {
            showEndPicker.value = true
        }
    )
}

@Composable
private fun AnalysisSumInfo(
    sum: BigDecimal,
    categoryStatsModels: List<CategoryStatsModel>,
) {
    CustomListItem(
        modifier = Modifier
            .height(56.dp),
        headlineContent = {
            Text(
                text = stringResource(R.string.sum),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = "$sum ${categoryStatsModels.firstOrNull()?.currency?.toCurrencySymbol() ?: " "}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    )
}

@Composable
fun AnalysisInfoItemWithChips(
    @StringRes leadingTextResId: Int,
    trailingText: String,
    onClick: () -> Unit,
) {
    CustomListItem(
        modifier = Modifier
            .height(57.dp)
            .clickable {
                onClick()
            },
        headlineContent = {
            Text(
                text = stringResource(leadingTextResId),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Green)
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = trailingText,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    )
}

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
        color = MaterialTheme.colorScheme.onSurface,
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