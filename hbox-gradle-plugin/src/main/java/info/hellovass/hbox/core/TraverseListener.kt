package info.hellovass.hbox.core

import java.io.File

interface TraverseListener {

    /**
     * 扫描文件夹
     */
    var onDirScan: (dir: File) -> Unit

    /**
     * 扫描 jar
     */
    var onJarScan: (jar: File) -> Unit
}

class DefaultTraverseListener(
    override var onDirScan: (dir: File) -> Unit = {},
    override var onJarScan: (jar: File) -> Unit = {}
) : TraverseListener