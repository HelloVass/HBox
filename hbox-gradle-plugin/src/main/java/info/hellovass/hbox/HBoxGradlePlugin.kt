package info.hellovass.hbox

import info.hellovass.hbox.core.BaseTransformPlugin
import java.io.File

class HBoxGradlePlugin : BaseTransformPlugin<HBoxExtensions>() {

    override val pluginName: String
        get() = Config.PLUGIN_NAME

    override val extensionsName: String
        get() = Config.EXTENSIONS_NAME

    override fun onScanBegin() {

    }

    override fun onScanClassFile(file: File, className: String, originScannedDirOrJar: File) {

    }

    override fun onScanEnd() {

    }

    override fun canSkipScan(jar: File): Boolean {
        return true
    }
}