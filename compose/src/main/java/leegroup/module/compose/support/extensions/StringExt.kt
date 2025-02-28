package leegroup.module.compose.support.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

fun String.hexToColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

fun randomString(words: Int): String = LoremIpsum(words).values.joinToString()

@Suppress("MagicNumber")
fun String?.appVersionToInt(): Int {
    return this?.split(".")?.mapIndexed { index, value ->
        when (index) {
            0 -> value.toIntOrNull().orZero() * 1000
            1 -> value.toIntOrNull().orZero() * 100
            else -> value.toIntOrNull().orZero()
        }
    }.orEmpty().sumOf { it }
}