package francisco.simon.myfinance.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data object ExpenseGraph {
    @Serializable
    data object ExpenseRoute

    @Serializable
    data object ExpensesHistoryRoute
}

@Serializable
data object IncomeGraph {

    @Serializable
    data object IncomeRoute

    @Serializable
    data object IncomeHistoryRoute
}

@Serializable
data object AccountGraph {
    @Serializable
    data object AccountRoute
}

@Serializable
data object CategoryGraph {
    @Serializable
    data object CategoryRoute
}

@Serializable
data object SettingsGraph {
    @Serializable
    data object SettingsRoute
}

@Serializable
data object SplashRoute

fun NavBackStackEntry?.routeClass(): KClass<*>? {
    return this?.destination.routeClass()
}

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