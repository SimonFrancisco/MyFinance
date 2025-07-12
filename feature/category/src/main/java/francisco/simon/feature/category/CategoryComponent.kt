package francisco.simon.feature.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import francisco.simon.feature.category.di.CategoryComponent
import francisco.simon.feature.category.di.CategoryDependenciesProvider
import francisco.simon.feature.category.di.DaggerCategoryComponent

@Composable
internal fun categoryComponent(): CategoryComponent {
    val context = LocalContext.current.applicationContext
    val categoriesDependencies = remember {
        (context as CategoryDependenciesProvider).getCategoryDependencies()
    }
    val component = remember(categoriesDependencies) {
        DaggerCategoryComponent.builder().categoryDependencies(categoriesDependencies).build()
    }
    return component
}