package info.hellovass.hbox.core

import com.android.build.api.transform.*
import info.hellovass.hbox.utils.LogUtils
import java.io.File

class TransformInvocationApiAdapter(
    override val real: TransformInvocation
) : TransformInvocationApi {

    private val outputProvider = real.outputProvider

    private val inputs = real.inputs

    override fun traverseAllOutput(init: TraverseListener.() -> Unit) {
        val dsl: TraverseListener = DefaultTraverseListener()
        dsl.init()
        inputs.forEach { input: TransformInput ->
            // Src
            input.directoryInputs.forEach { inputDir: DirectoryInput ->
                // 获取 output 目录
                val dest: File = outputProvider.getContentLocation(
                    inputDir.name,
                    inputDir.contentTypes,
                    inputDir.scopes,
                    Format.DIRECTORY
                )
                // 将 input 的目录复制到 output 指定目录
                copyDirectory(
                    srcDir = inputDir.file,
                    destDir = dest
                )
                dsl.onDirScan(dest)
                LogUtils.info("scan dir: ${inputDir.file} -> $dest")
            }
            // Jar
            input.jarInputs.forEach { jarInput: JarInput ->
                // 获取 output 目录
                val dest: File = outputProvider.getContentLocation(
                    jarInput.name,
                    jarInput.contentTypes,
                    jarInput.scopes,
                    Format.JAR
                )
                // 将 input 的目录复制到 output 指定目录
                copyFile(
                    srcFile = jarInput.file,
                    destFile = dest
                )
                dsl.onJarScan(dest)
                LogUtils.info("scar jar: ${jarInput.name} -> $dest")
            }
        }
    }

    override fun deleteAllOutputProvider() {
        outputProvider.deleteAll()
    }
}