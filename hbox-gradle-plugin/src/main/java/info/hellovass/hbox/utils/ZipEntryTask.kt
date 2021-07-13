package info.hellovass.hbox.utils

import com.google.common.io.ByteStreams
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.util.concurrent.Callable
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class ZipEntryTask(
    private val zipFile: ZipFile,
    private val zipEntry: ZipEntry
) : Callable<ClassEntity> {

    override fun call(): ClassEntity {
        val bytes = zipFile.getInputStream(zipEntry).use { input ->
            when {
                zipEntry.name.endsWith(".class") ->
                    operate(ByteStreams.toByteArray(input))
                else ->
                    ByteStreams.toByteArray(input)
            }
        }
        return ClassEntity(
            name = zipEntry.name,
            data = bytes
        )
    }

    private fun operate(bytes: ByteArray): ByteArray {
        val classReader = ClassReader(bytes)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val classVisitor = OperateClassVisitor(Opcodes.ASM7, classWriter)
        classReader.accept(classVisitor, 0)
        return classWriter.toByteArray()
    }
}