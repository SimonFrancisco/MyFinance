package francisco.simon.myfinance.core.mapper

import francisco.simon.myfinance.R

fun String.toVectorRes(): Int {
    return when (this) {
        "\uD83C\uDFE1" -> R.drawable.ic_house_with_garden
        "\uD83D\uDC57" -> R.drawable.ic_dress
        "\uD83D\uDC36" -> R.drawable.ic_dog_face
        "РК" -> R.drawable.ic_pk
        "\uD83C\uDF6D" -> R.drawable.ic_lollipop
        "\uD83C\uDFCB\u200D♂\uFE0F" -> R.drawable.ic_person_lifting_weights
        "\uD83D\uDC8A" -> R.drawable.ic_pill
        else -> R.drawable.ic_launcher_foreground
    }
}

fun String.toCurrencySymbol(): String {
    return when (this) {
        "RUB" -> "₽"
        else -> ""
    }
}