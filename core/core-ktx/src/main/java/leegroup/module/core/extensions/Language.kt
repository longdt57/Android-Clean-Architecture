package leegroup.module.core.extensions

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

fun Context.setAppLocale(locale: Locale) {
    val tags = locale.toLanguageTag()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSystemService(LocaleManager::class.java)?.applicationLocales =
            LocaleList.forLanguageTags(tags)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tags))
    }
}

fun Context.getAppLocale(): Locale =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        getSystemService(LocaleManager::class.java)?.applicationLocales?.get(0)
            ?: Locale.getDefault()
    else
        @Suppress("DEPRECATION") resources.configuration.locales[0]

