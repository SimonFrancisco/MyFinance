package francisco.simon.core.domain.utils.theme

import francisco.simon.core.domain.utils.theme.MyFinanceColorScheme.GREEN

enum class MyFinanceColorScheme {
    GREEN,
    RED,
    YELLOW,
    BLUE
}

fun getColorSchemeByName(name: String?): MyFinanceColorScheme {
    return MyFinanceColorScheme.entries.find {
        it.name == name
    } ?: GREEN
}