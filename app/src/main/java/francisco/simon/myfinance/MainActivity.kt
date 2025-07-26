package francisco.simon.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import francisco.simon.core.domain.utils.theme.MyFinanceThemeMode
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.AppTopBar
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel: MyFinanceViewModel = viewModel(
                factory = component.viewModelFactory()
            )
            val themeMode = viewModel.themeMode.collectAsStateWithLifecycle()
            val isDarkTheme = when (themeMode.value) {
                MyFinanceThemeMode.DARK -> true
                MyFinanceThemeMode.LIGHT -> false
            }
            val colorScheme = viewModel.colorScheme.collectAsStateWithLifecycle()
            viewModel.setApiVersion(BuildConfig.VERSION_NAME)

            val locale = viewModel.locale.collectAsStateWithLifecycle()
            val localizedContext = remember(locale.value) {
                applicationContext.applyLocale(locale.value)
            }
            CompositionLocalProvider(
                LocalContext provides localizedContext
            ) {
                MyFinanceTheme(
                    darkTheme = isDarkTheme,
                    myFinanceColorScheme = colorScheme.value
                ) {
                    FinanceApp()
                }
            }

        }
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



