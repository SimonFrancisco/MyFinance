package francisco.simon.myfinance.ui.features.category.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.ui.features.category.model.CategoryUI

@Composable
fun CategoryScreen(appBarConfig: (AppBarState) -> Unit) {
    LaunchedEffect(Unit) {
        appBarConfig(
            AppBarState(
                titleRes = R.string.category_app_top_bar
            )
        )
    }
    val viewModel: CategoryViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.RESUMED
    )
    val currentState = state.value
    CategoryScreenContent(currentState)
}

@Composable
fun CategoryScreenContent(
    state: CategoryScreenState
) {
    when (state) {
        is CategoryScreenState.Error -> {

        }

        is CategoryScreenState.Loading -> {
            FullScreenLoading()
        }

        is CategoryScreenState.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchCategory()
                HorizontalDivider()
                CategoryScreenList(state.categories)
            }
        }
    }
}


@Composable
fun CategoryScreenList(
    categories: List<CategoryUI>
) {
    LazyColumn {
        items(categories, key = { it.id }) { category ->
            CustomListItem(
                modifier = Modifier
                    .height(70.dp)
                    .clickable {

                    },
                headlineContent = {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                },
                leadingContent = {
                    Image(
                        imageVector = ImageVector.vectorResource(category.emojiRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    )
                    Spacer(Modifier.width(16.dp))
                }
            )
            HorizontalDivider()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCategory(
    modifier: Modifier = Modifier
) {
    val query = rememberSaveable {
        mutableStateOf("")
    }
    SearchBar(
        shape = RectangleShape,
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(SearchBarDefaults.colors().containerColor),
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.fillMaxHeight(),
                query = query.value,
                onQueryChange = {
                    query.value = it
                },
                placeholder = {
                    Text(
                        stringResource(R.string.search_bar_find_category),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                onSearch = {

                }, expanded = false,
                onExpandedChange = {
                },
                trailingIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_trailing_icon),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            )
        }, expanded = false,
        onExpandedChange = {
        }
    ) {

    }


}