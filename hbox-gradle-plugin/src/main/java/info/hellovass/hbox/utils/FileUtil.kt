package info.hellovass.hbox.core

import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.codehaus.groovy.runtime.ResourceGroovyMethods
import org.gradle.kotlin.dsl.closureOf
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.charset.Charset

fun copyDirectory(
    srcDir: File,
    destDir: File
) = FileUtils.copyDirectory(
    srcDir,
    destDir
)

fun copyFile(
    srcFile: File,
    destFile: File
) = FileUtils.copyFile(
    srcFile,
    destFile
)

fun File.eachFileRecurse(
    fileType: FileType = FileType.FILES,
    action: (File) -> Unit
) = ResourceGroovyMethods.eachFileRecurse(
    this,
    fileType,
    closureOf(action)
)

fun File.toByteArray(): ByteArray = IOUtils.toByteArray(
    FileInputStream(this)
)

fun InputStream.toByteArray(): ByteArray = IOUtils.toByteArray(
    this
)

fun File.write(data: CharSequence) = FileUtils.write(
    this,
    data,
    Charset.defaultCharset()
)

fun File.forceDelete() = FileUtils.forceDelete(
    this
)

fun writeByteArrayToFile(
    byteArray: ByteArray,
    file: File
) = FileUtils.writeByteArrayToFile(
    file,
    byteArray
)