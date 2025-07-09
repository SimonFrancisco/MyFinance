@file:OptIn(ExperimentalMaterial3Api::class)

package francisco.simon.myfinance.ui.features.category.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.components.CustomListItem
import francisco.simon.myfinance.core.components.FullScreenLoading
import francisco.simon.myfinance.core.components.RetryCall
import francisco.simon.myfinance.core.components.topBar.AppBarState
import francisco.simon.myfinance.core.components.topBar.topBarUpdate.UpdateAppBarState
import francisco.simon.myfinance.getApplicationComponent
import francisco.simon.myfinance.ui.features.category.model.CategoryUI

/**
 * Category Screen, separate concerns to avoid unnecessary recompositions and
 * keep code logic short
 * @author Simon Francisco
 */
@Composable
fun CategoryScreen(appBarState: MutableState<AppBarState>) {
    UpdateAppBarState(
        appBarState = appBarState,
        titleRes = R.string.category_app_top_bar
    )
    val component = getApplicationComponent()
    val viewModel: CategoryViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    CategoryScreenContent(currentState, viewModel)
}

@Composable
private fun CategoryScreenContent(
    state: CategoryScreenState,
    viewModel: CategoryViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchCategory(viewModel = viewModel)
        HorizontalDivider()
        when (state) {
            is CategoryScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = {
                        viewModel.retry()
                    },
                )
            }

            is CategoryScreenState.Loading -> {
                FullScreenLoading()
            }

            is CategoryScreenState.Success -> {
                CategoryScreenList(state.categories)
            }
        }
    }

}


@Composable
private fun CategoryScreenList(
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
                    CategoryHeadingContent(category)
                },
                leadingContent = {
                    CategoryLeadingContent(category)
                    Spacer(Modifier.width(16.dp))
                }
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun CategoryLeadingContent(category: CategoryUI) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(category.emoji)
    }
}

@Composable
private fun CategoryHeadingContent(category: CategoryUI) {
    Text(
        text = category.name,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun SearchCategory(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel
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
            SearchBarInputField(query, viewModel)
        }, expanded = false,
        onExpandedChange = {
        }
    ) {}
}

@Composable
private fun SearchBarInputField(query: MutableState<String>, viewModel: CategoryViewModel) {
    SearchBarDefaults.InputField(
        modifier = Modifier.fillMaxHeight(),
        query = query.value,
        onQueryChange = {
            query.value = it
            viewModel.searchCategory(query.value)
        },
        placeholder = {
            Text(
                stringResource(R.string.search_bar_find_category),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        onSearch = {}, expanded = false,
        onExpandedChange = {},
        trailingIcon = {
            SearchBarTrailingIcon()
        }
    )
}

@Composable
private fun SearchBarTrailingIcon() {
    IconButton(onClick = {}) {
        Icon(
            painter = painterResource(R.drawable.ic_trailing_icon),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
    }
}