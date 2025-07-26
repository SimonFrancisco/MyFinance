package francisco.simon.myfinance

import android.content.Context
import java.util.Locale

fun Context.applyLocale(locale: Locale): Context {
    val config = resources.configuration
    config.setLocale(locale)
    return createConfigurationContext(config)
}