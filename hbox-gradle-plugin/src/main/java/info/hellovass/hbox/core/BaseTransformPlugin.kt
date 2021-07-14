package info.hellovass.hbox.core

import com.android.SdkConstants
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import info.hellovass.hbox.utils.LogUtils
import info.hellovass.hbox.utils.StringUtils.minus
import info.hellovass.hbox.utils.stringify
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.jar.JarFile

abstract class BaseTransformPlugin<T : Any> : Plugin<Project>, TransformCallback {

    private lateinit var project: Project

    @Suppress("UNCHECKED_CAST")
    protected val extensions: T
        get() = project.extensions.getByName(extensionsName) as T

    protected abstract val pluginName: String

    protected abstract val extensionsName: String

    override fun apply(target: Project) {

        if (!target.plugins.hasPlugin(AppPlugin::class.java))
            return

        this.project = target
        // 初始化日志打印工具
        LogUtils.initialize(gradle = target.gradle)
        // 创建扩展
        project.extensions.create(
            extensionsName,
            getGenericClass()
        )
        // 注册 transform
        project.extensions.getByType(AppExtension::class.java).apply {
            registerTransform(
                BaseTransform(
                    pluginName,
                    extensions,
                    this@BaseTransformPlugin
                )
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getGenericClass(): Class<T> {
        val superClass: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        val type: Type = superClass.actualTypeArguments.first()
        return type as Class<T>
    }

    class BaseTransform<T : Any>(
        private val pluginName: String,
        private val extensions: T,
        private val callback: TransformCallback
    ) : Transform() {

        override fun getName(): String {
            return pluginName
        }

        override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
            return TransformManager.CONTENT_CLASS
        }

        override fun isIncremental(): Boolean {
            return false
        }

        override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
            return TransformManager.SCOPE_FULL_PROJECT
        }

        override fun transform(transformInvocation: TransformInvocation) {
            super.transform(transformInvocation)

            // 开始计时
            LogUtils.info("$pluginName started")
            val timeStartAt = System.currentTimeMillis()

            // 打印 Extensions
            LogUtils.info("$pluginName extensions => ${extensions.stringify()}")

            // 适配 transformInvocation
            val transformApi = TransformInvocationApiAdapter(real = transformInvocation)

            //  如果不支持增量编译，删除所有输出目录
            if (!isIncremental) {
                transformApi.deleteAllOutputProvider()
            }

            // 扫描开始
            callback.onScanBegin()
            // 扫描中
            transformApi.traverseAllOutput {
                onDirScan = { dir: File ->
                    scanDir(dir)
                }
                onJarScan = onJarScan@{ jar: File ->
                    if (callback.canSkipScan(jar)) {
                        LogUtils.info("skip jar => $jar")
                        return@onJarScan
                    }
                    scanJar(jar)
                }
            }
            // 扫描结束
            callback.onScanEnd()
            // 记录时长
            LogUtils.info("$pluginName finished:${System.currentTimeMillis() - timeStartAt}ms")
        }

        private fun scanDir(dir: File) {
            scanDir(dir, dir)
        }

        private fun scanJar(jar: File) {
            scanDir(JarFile(jar))
//            val temp = File(jar.parent, "temp_${jar.name}")
//            val unzipFile = ZipUtil.unzipTo(jar, temp)
//            if (unzipFile.isNotEmpty()) {
//                scanDir(temp, jar)
//                temp.forceDelete()
//            }
        }

        private fun scanDir(dir: File, originScannedDirOrJar: File) {

            if (!dir.isDirectory)
                return

            // 检查是否以 File.separator 结尾
            var rootPath = dir.absolutePath
            if (!rootPath.endsWith(File.separator)) {
                rootPath += File.separator
            }

            // 遍历所有文件
            dir.eachFileRecurse { file: File ->

                val fileName = file.name

                if (!fileName.endsWith(SdkConstants.DOT_CLASS)
                    || fileName.startsWith("R$")
                    || fileName == SdkConstants.FN_COMPILED_RESOURCE_CLASS
                    || fileName == "BuildConfig.class"
                ) {
                    return@eachFileRecurse
                }

                val filePath = file.absolutePath
                val packagePath = filePath.replace(rootPath, "")
                val className = packagePath.replace(File.separator, ".") - SdkConstants.DOT_CLASS

                callback.onScanClassFile(
                    file,
                    className,
                    originScannedDirOrJar
                )
            }
        }
    }
}