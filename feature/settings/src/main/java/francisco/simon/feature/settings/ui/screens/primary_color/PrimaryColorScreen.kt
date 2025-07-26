package francisco.simon.feature.settings.ui.screens.primary_color

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme
import francisco.simon.core.ui.R
import francisco.simon.core.ui.components.FullScreenLoading
import francisco.simon.core.ui.components.topBar.AppBarState
import francisco.simon.core.ui.components.topBar.NavigationButton
import francisco.simon.core.ui.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.core.ui.theme.colors.blue.darkBlueScheme
import francisco.simon.core.ui.theme.colors.green.darkGreenScheme
import francisco.simon.core.ui.theme.colors.red.darkRedScheme
import francisco.simon.core.ui.theme.colors.yellow.darkYellowScheme
import francisco.simon.feature.settings.settingsComponent

@Composable
fun PrimaryColorScreen(
    appBarState: MutableState<AppBarState>,
    onGoBackToSettingScreen: () -> Unit
) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.primary_color_app_top_bar,
        navigationButton = NavigationButton.Back {
            onGoBackToSettingScreen()
        }
    )
    val component = settingsComponent()
    val viewModel: PrimaryColorScreenViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    LaunchedEffect(Unit) {
        viewModel.getColorScheme()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    PrimaryScreenContent(
        state = currentState,
        viewModel = viewModel
    )
}

@Composable
private fun PrimaryScreenContent(
    state: PrimaryColorScreenState,
    viewModel: PrimaryColorScreenViewModel
) {
    when (state) {
        is PrimaryColorScreenState.Loading -> {
            FullScreenLoading()
        }

        is PrimaryColorScreenState.Success -> {
            PickPrimaryColor(
                onSuccess = state,
                onColorSelected = { colorWithScheme ->
                    viewModel.setColorScheme(colorWithScheme.colorScheme)
                }
            )
        }
    }
}

@Composable
private fun PickPrimaryColor(
    onSuccess: PrimaryColorScreenState.Success,
    onColorSelected: (ColorWithColorScheme) -> Unit
) {
    val availableColors = listOf(
        ColorWithColorScheme(
            darkGreenScheme.primary,
            MyFinanceColorScheme.GREEN
        ),
        ColorWithColorScheme(
            darkRedScheme.primary,
            MyFinanceColorScheme.RED
        ),
        ColorWithColorScheme(
            darkYellowScheme.primary,
            MyFinanceColorScheme.YELLOW
        ),
        ColorWithColorScheme(
            darkBlueScheme.primary,
            MyFinanceColorScheme.BLUE
        )
    )
    val selectedColor = availableColors.first {
        it.colorScheme == onSuccess.colorScheme
    }.color


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            availableColors.forEach { colorWithScheme ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(colorWithScheme.color)
                        .border(
                            width = if (colorWithScheme.color == selectedColor) 4.dp else 2.dp,
                            color = if (colorWithScheme.color == selectedColor) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                        .clickable {
                            onColorSelected(colorWithScheme)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (colorWithScheme.color == selectedColor) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Immutable
private data class ColorWithColorScheme(
    val color: Color,
    val colorScheme: MyFinanceColorScheme
)