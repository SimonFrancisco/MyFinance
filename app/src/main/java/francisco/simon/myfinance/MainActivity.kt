package francisco.simon.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import francisco.simon.myfinance.core.components.navigationBar.AppNavigationBar
import francisco.simon.myfinance.core.components.navigationBar.mainTabs
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.AppTopBar
import francisco.simon.myfinance.core.components.topBar.LocalAppBarState
import francisco.simon.myfinance.ui.navigation.AccountGraph.AccountRoute
import francisco.simon.myfinance.ui.navigation.AppNavGraph
import francisco.simon.myfinance.ui.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.myfinance.ui.navigation.IncomeGraph.IncomeRoute
import francisco.simon.myfinance.ui.navigation.SplashRoute
import francisco.simon.myfinance.ui.navigation.routeClass
import francisco.simon.myfinance.ui.theme.Green
import francisco.simon.myfinance.ui.theme.GreyLight
import francisco.simon.myfinance.ui.theme.MyFinanceTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        edgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            MyFinanceTheme {
                FinanceApp()
            }
        }

    }

    private fun edgeToEdge() {
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                GreyLight.toArgb(),
                GreyLight.toArgb()
            ),
            statusBarStyle = SystemBarStyle.light(
                Green.toArgb(),
                Green.toArgb()
            )
        )
    }
}

@Composable
fun FinanceApp() {
    val navController = rememberNavController()
    val appBarState = remember { AppBarState(R.string.expense_app_top_bar) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val floatButtonScreens = listOf(
        IncomeRoute::class, ExpenseRoute::class, AccountRoute::class
    )
    CompositionLocalProvider(
        LocalAppBarState provides appBarState
    ) {
        Scaffold(
            bottomBar = {
                if (currentBackStackEntry.routeClass() != SplashRoute::class) {
                    AppNavigationBar(
                        navController = navController,
                        tabs = mainTabs
                    )
                }

            },
            topBar = {
                if (currentBackStackEntry.routeClass() != SplashRoute::class) {
                    AppTopBar(
                        appBarState = appBarState
                    )
                }

            },
            floatingActionButton = {
                if (currentBackStackEntry.routeClass() in floatButtonScreens) {
                    FloatingActionButton(
                        containerColor = Green,
                        shape = CircleShape,
                        modifier = Modifier
                            .size(56.dp),
                        onClick = {

                        }
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                }
            }
        ) { innerPadding ->
            AppNavGraph(
                navController = navController,
                startDestination = SplashRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }

}



