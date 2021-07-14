package info.hellovass.hbox

object Config {

    // 扩展参数名
    const val EXTENSIONS_NAME = "HBox"

    // 插件名
    const val PLUGIN_NAME = "hbox-gradle-plugin"

    // 排除的三方库
    val EXCLUDE_LIBS = listOf(
        "com.android.support",
        "androidx",
        "com.google",
        "android.arch",
        "org.jetbrains",
        "com.squareup",
        "org.greenrobot",
        "com.github.bumptech.glide"
    )
}