package leegroup.module.designsystem.support.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.core.graphics.toColorInt

fun String.hexToColor(): Color {
    return Color(toColorInt())
}

fun randomString(words: Int): String = LoremIpsum(words).values.joinToString()

@Suppress("MagicNumber")
fun String?.appVersionToInt(): Int {
    fun Int?.orZero() = this ?: 0

    return this?.split(".")?.mapIndexed { index, value ->
        when (index) {
            0 -> value.toIntOrNull().orZero() * 1000
            1 -> value.toIntOrNull().orZero() * 100
            else -> value.toIntOrNull().orZero()
        }
    }.orEmpty().sumOf { it }
}