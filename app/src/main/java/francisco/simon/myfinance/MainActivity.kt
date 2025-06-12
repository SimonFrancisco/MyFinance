package francisco.simon.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import francisco.simon.myfinance.core.components.navigationBar.AppNavigationBar
import francisco.simon.myfinance.core.components.navigationBar.mainTabs
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.AppTopBar
import francisco.simon.myfinance.core.components.topBar.LocalAppBarState
import francisco.simon.myfinance.ui.navigation.AppNavGraph
import francisco.simon.myfinance.ui.navigation.ExpenseGraph
import francisco.simon.myfinance.ui.theme.Green
import francisco.simon.myfinance.ui.theme.MyFinanceTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Green.toArgb(),
                Green.toArgb()
            ),
            statusBarStyle = SystemBarStyle.light(
                Green.toArgb(),
                Green.toArgb()
            )
        )
        setContent {
            MyFinanceTheme {
                FinanceApp()
            }
        }
    }
}

@Composable
fun FinanceApp() {
    val navController = rememberNavController()
    val appBarState = remember { AppBarState(R.string.expense_app_top_bar) }
    CompositionLocalProvider(
        LocalAppBarState provides appBarState
    ) {
        Scaffold(
            bottomBar = {
                AppNavigationBar(
                    navController = navController,
                    tabs = mainTabs
                )
            },
            topBar = {
                AppTopBar(
                    appBarState = appBarState
                )
            }
        ) { innerPadding ->
            AppNavGraph(
                navController = navController,
                startDestination = ExpenseGraph,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }

}



