package info.hellovass.hbox.utils

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.util.concurrent.Future
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * 解压 Jar
 *
 * @param file
 * @return
 */
fun unzip(file: File): List<Future<ClassEntity>> {
    return ZipFile(file, Charset.defaultCharset()).use { zipFile ->
        return@use zipFile.entries()
            .asSequence()
            .map { zipEntry ->
                ThreadPool.executor.submit(
                    ZipEntryTask(
                        zipFile = zipFile,
                        zipEntry = zipEntry
                    )
                )
            }
            .toList()
    }
}

/**
 * 复写 File 里的内容
 *
 * @param file
 * @param classEntities
 */
fun reWriteJar(file: File, classEntities: List<ClassEntity>) {
    JarOutputStream(BufferedOutputStream(FileOutputStream(file))).use { jos ->
        classEntities.forEach { classEntity ->
            jos.putNextEntry(ZipEntry(classEntity.name))
            jos.write(classEntity.data)
        }
    }
}


