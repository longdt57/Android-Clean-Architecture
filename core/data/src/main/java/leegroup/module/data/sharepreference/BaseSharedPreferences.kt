package leegroup.module.data.sharepreference

import android.content.Context
import android.content.SharedPreferences

abstract class BaseSharedPreferences(
    applicationContext: Context,
    prefName: String
) {

    protected open val sharedPreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun remove(key: String) {
        sharedPreferences.execute { it.remove(key) }
    }

    fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }
}
