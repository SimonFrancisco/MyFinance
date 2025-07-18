package francisco.simon.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.AppTopBar
import francisco.simon.core.ui.theme.Green
import francisco.simon.core.ui.theme.GreyLight
import francisco.simon.core.ui.theme.MyFinanceTheme
import francisco.simon.feature.expenses.navigation.ExpenseGraph.ExpenseRoute
import francisco.simon.feature.expenses.ui.screens.expense.ExpenseFloatingButton
import francisco.simon.feature.income.navigation.IncomeGraph.IncomeRoute
import francisco.simon.feature.income.ui.screens.income.IncomeFloatingButton
import francisco.simon.myfinance.navigation.AppNavGraph
import francisco.simon.myfinance.navigation.SplashRoute
import francisco.simon.myfinance.navigation.routeClass
import francisco.simon.myfinance.navigationBar.AppNavigationBar
import francisco.simon.myfinance.navigationBar.mainTabs

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

/**
 * The whole app functionality is joined here.
 * @author Simon Francisco
 */
@Composable
private fun FinanceApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val appBarState = remember {
        mutableStateOf(AppBarState(R.string.expense_app_top_bar))
    }
    Scaffold(
        bottomBar = {
            BottomBarSettings(currentBackStackEntry, navController)
        },
        topBar = {
            TopBarSettings(currentBackStackEntry, appBarState.value)
        },
        floatingActionButton = {
            FloatingButtonSettings(currentBackStackEntry)
        }
    ) { innerPadding ->
        AppNavGraphSettings(
            navController = navController,
            appBarState = appBarState,
            innerPadding = innerPadding,
        )
    }

}

@Composable
private fun TopBarSettings(
    currentBackStackEntry: NavBackStackEntry?,
    appBarState: AppBarState
) {
    if (currentBackStackEntry.routeClass() != SplashRoute::class) {
        AppTopBar(
            appBarState = appBarState
        )
    }
}

@Composable
private fun BottomBarSettings(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {
    if (currentBackStackEntry.routeClass() != SplashRoute::class) {
        AppNavigationBar(
            navController = navController,
            tabs = mainTabs
        )
    }
}

@Composable
private fun FloatingButtonSettings(
    currentBackStackEntry: NavBackStackEntry?,
) {
    if (currentBackStackEntry.routeClass() == IncomeRoute::class) {
        IncomeFloatingButton()
    }
    if (currentBackStackEntry.routeClass() == ExpenseRoute::class) {
        ExpenseFloatingButton()
    }
}

@Composable
private fun AppNavGraphSettings(
    navController: NavHostController,
    appBarState: MutableState<AppBarState>,
    innerPadding: PaddingValues,
) {
    AppNavGraph(
        navController = navController,
        appBarState = appBarState,
        startDestination = SplashRoute,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    )
}



