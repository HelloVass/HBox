package info.hellovass.hbox

open class HBoxExtensions {

    /**
     * 只扫描哪些库的正则表达式
     */
    var onlyScanLibRegex: String = ""

    /**
     * 跳过扫描的正则表达式
     */
    var jumpScanLibRegex: String = ""

    /**
     * 扫描有问题时是否终止编译，默认为 true
     */
    var abortOnError: Boolean = true
}