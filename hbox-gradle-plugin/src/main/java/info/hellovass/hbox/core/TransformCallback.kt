package info.hellovass.hbox.core

import java.io.File

interface TransformCallback {

    /**
     * 扫描开始
     */
    fun onScanBegin()

    /**
     * 扫描 .class 文件
     */
    fun onScanClassFile(
        file: File,
        className: String,
        originScannedDirOrJar: File
    )

    /**
     * 扫描结束
     */
    fun onScanEnd()

    /**
     * 是否可以跳过
     */
    fun canSkipScan(jar: File): Boolean
}