package francisco.simon.myfinance.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

/**
 * File contains all nav graphs with needed routes
 * @author Simon
 */
@Serializable
data object ExpenseGraph {
    @Serializable
    data object ExpenseRoute

    @Serializable
    data object ExpensesHistoryRoute
}


@Serializable
data object SettingsGraph {
    @Serializable
    data object SettingsRoute
}

@Serializable
data object SplashRoute

/**
 * Nav back stack entry to class used as route
 * @author Simon Francisco
 */
fun NavBackStackEntry?.routeClass(): KClass<*>? {
    return this?.destination.routeClass()
}

/**
 * Nav destination to class used as route
 * @author Simon Francisco
 */
fun NavDestination?.routeClass(): KClass<*>? {
    return this?.route
        ?.split("/")
        ?.first()
        ?.let { className ->
            generateSequence(className, ::replaceLastDotByDollar)
                .mapNotNull(::tryParseClass)
                .firstOrNull()
        }
}

private fun tryParseClass(className: String): KClass<*>? {
    return runCatching { Class.forName(className).kotlin }.getOrNull()
}

private fun replaceLastDotByDollar(input: String): String? {
    val index = input.lastIndexOf('.')
    return if (index != 1) {
        String(input.toCharArray().apply {
            set(index, '$')
        })
    } else {
        null
    }
}