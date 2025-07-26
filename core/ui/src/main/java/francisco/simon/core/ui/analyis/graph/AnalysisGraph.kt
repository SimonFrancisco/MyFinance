package francisco.simon.core.ui.analyis.graph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import francisco.simon.core.ui.R
import francisco.simon.core.ui.analyis.graph.model.CategoryGraphModelUi

@Composable
internal fun AnalysisGraph(
    modifier: Modifier = Modifier,
    items: List<CategoryGraphModelUi>,
    animationDuration: Int = 1000
) {
    val context = LocalContext.current
    val categories = categoryGraphModelList(
        items = items,
        lastItemText = context.getString(R.string.others)
    )

    val totalPercentage = categories.sumOf {
        it.percentage.toDouble()
    }.toFloat()
    if (totalPercentage == 0f)
        return

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(categories) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    val arcsAngle = categories.map {
        360 * (it.percentage / totalPercentage)
    }
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val strokeWidth = 12.dp.toPx()
        val graphSize = if (maxWidth < maxHeight) {
            maxWidth
        } else {
            maxHeight
        }
        Canvas(
            modifier = Modifier.size(graphSize)
        ) {
            var startAngle = -90f
            categories.forEachIndexed { index, category ->
                drawArc(
                    color = category.color,
                    startAngle = startAngle,
                    sweepAngle = arcsAngle[index] * animatedProgress.value,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                startAngle += arcsAngle[index] * animatedProgress.value
            }
        }
        Column(
            modifier = Modifier.width(graphSize - strokeWidth.dp - 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            categories.forEach { category ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(4.dp),
                        shape = CircleShape,
                        color = category.color
                    ) {}
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${"%.1f".format(category.percentage)} % ${category.name}",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

    }

}


private fun categoryGraphModelList(
    items: List<CategoryGraphModelUi>,
    lastItemText: String,
): List<CategoryGraphModelUi> {
    if (items.size <= 5) {
        return items.sortedByDescending { it.percentage }
    }
    val sortedByPercent = items.sortedByDescending { it.percentage }
    val biggestItems = sortedByPercent.take(4)
    val lastItem = sortedByPercent[4]
    val otherItemsPercentage = sortedByPercent.drop(4)
        .sumOf { it.percentage.toDouble() }.toFloat()
    val otherItems = lastItem.copy(
        name = lastItemText,
        percentage = otherItemsPercentage,
        color = Color.Gray
    )
    return biggestItems + otherItems
}

@Composable
private fun Dp.toPx() = with(LocalDensity.current) {
    this@toPx.toPx()
}