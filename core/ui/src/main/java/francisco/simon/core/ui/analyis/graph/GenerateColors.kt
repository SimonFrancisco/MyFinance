package francisco.simon.core.ui.analyis.graph

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

internal fun generateColorForCategory(categoryId: Int): Color {
    val categoryColors = categoryColors()
    val index = abs(categoryId.hashCode()) % categoryColors.size
    return categoryColors[index]
}

private fun categoryColors(): List<Color> {
    return listOf(
        Color(0xFFE57373),
        Color(0xFFF06292),
        Color(0xFFBA68C8),
        Color(0xFF9575CD),
        Color(0xFF7986CB),
        Color(0xFF64B5F6),
        Color(0xFF4FC3F7),
        Color(0xFF4DD0E1),
        Color(0xFF4DB6AC),
        Color(0xFF81C784),
        Color(0xFFAED581),
        Color(0xFFDCE775),
        Color(0xFFFFD54F),
        Color(0xFFFFB74D),
        Color(0xFFA1887F),
        Color(0xFFE0E0E0),
        Color(0xFF90A4AE)
    )
}