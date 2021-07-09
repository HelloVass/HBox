package info.hellovass.hbox.utils

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

fun a(sourceFile: File, destFile: File) {
    JarOutputStream(FileOutputStream(destFile)).use { jos ->
        JarFile(sourceFile).use { jarFile ->
            jarFile.entries().asSequence()
                .forEach { jarEntry: JarEntry ->
                    val zipEntry = ZipEntry(jarEntry.name)
                    val bytes: ByteArray = jarFile.getInputStream(jarEntry).use {
                        when {
                            jarEntry.name.endsWith(".class") ->
                                operate(input2Bytes(it))
                            else ->
                                input2Bytes(it)
                        }
                    }
                    jos.putNextEntry(zipEntry)
                    jos.write(bytes)
                }
        }
    }
}

fun input2Bytes(inputStream: InputStream): ByteArray {
    return inputStream.use { input ->
        ByteArrayOutputStream().use { output ->
            output.apply {
                input.copyTo(this)
            }
        }
    }.toByteArray()
}

fun operate(bytes: ByteArray): ByteArray {
    val classReader = ClassReader(bytes)
    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
    val classVisitor = OperateClassVisitor(Opcodes.ASM7, classWriter)
    classReader.accept(classVisitor, 0)
    return classWriter.toByteArray()
}
