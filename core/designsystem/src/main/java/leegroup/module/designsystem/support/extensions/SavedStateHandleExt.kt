package leegroup.module.designsystem.support.extensions

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import leegroup.module.designsystem.support.util.JsonUtil

/**
 * Similar to [SavedStateHandle.toRoute]
 */
@Deprecated("Use [leegroup.module.compose.support.util.SavedStateProvider]")
inline fun <reified T : Any> SavedStateHandle.toNavModel(): T {
    val map = mutableMapOf<String, JsonElement>()

    for (key in keys()) {
        map[key] =
            when (val value = get<Any>(key)) {
                is String -> JsonPrimitive(value)
                is Int -> JsonPrimitive(value)
                is Long -> JsonPrimitive(value)
                is Float -> JsonPrimitive(value)
                is Double -> JsonPrimitive(value)
                is Byte -> JsonPrimitive(value)
                is Boolean -> JsonPrimitive(value)
                else -> JsonNull
            }
    }

    val jsonString = JsonUtil.encodeToString(map)
    return JsonUtil.decodeFromString(jsonString) ?: error("Illegal state $map")
}