package francisco.simon.myfinance.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

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