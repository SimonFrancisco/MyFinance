package francisco.simon.core.domain.utils.theme

enum class MyFinanceThemeMode {
    DARK,
    LIGHT
}

fun getThemeModeByName(name: String?): MyFinanceThemeMode {
    return MyFinanceThemeMode.entries.find {
        it.name == name
    } ?: MyFinanceThemeMode.LIGHT
}