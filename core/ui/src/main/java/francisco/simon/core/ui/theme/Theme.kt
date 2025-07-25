package francisco.simon.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import francisco.simon.core.ui.theme.colors.blue.darkBlueScheme
import francisco.simon.core.ui.theme.colors.blue.lightBlueScheme
import francisco.simon.core.ui.theme.colors.green.darkGreenScheme
import francisco.simon.core.ui.theme.colors.green.lightGreenScheme
import francisco.simon.core.ui.theme.colors.red.darkRedScheme
import francisco.simon.core.ui.theme.colors.red.lightRedScheme
import francisco.simon.core.ui.theme.colors.yellow.darkYellowScheme
import francisco.simon.core.ui.theme.colors.yellow.lightYellowScheme

@Composable
fun MyFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    myFinanceColorScheme: MyFinanceColorScheme = MyFinanceColorScheme.GREEN,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> when (myFinanceColorScheme) {
            MyFinanceColorScheme.GREEN -> {
                if (darkTheme) {
                    darkGreenScheme
                } else {
                    lightGreenScheme
                }
            }

            MyFinanceColorScheme.RED -> {
                if (darkTheme) {
                    darkRedScheme
                } else {
                    lightRedScheme
                }
            }

            MyFinanceColorScheme.YELLOW -> {
                if (darkTheme) {
                    darkYellowScheme
                } else {
                    lightYellowScheme
                }
            }

            MyFinanceColorScheme.BLUE -> {
                if (darkTheme) {
                    darkBlueScheme
                } else {
                    lightBlueScheme
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}