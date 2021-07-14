package info.hellovass.hbox.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.deserialize(): T? {
    val type = object : TypeToken<T>() {
        // ignore
    }.type
    return Gson().fromJson(this, type)
}

fun Any.stringify(): String {
    return Gson().toJson(this)
}
