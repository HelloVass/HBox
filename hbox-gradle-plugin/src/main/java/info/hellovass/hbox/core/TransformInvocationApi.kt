package info.hellovass.hbox.core

import com.android.build.api.transform.TransformInvocation

interface TransformInvocationApi {

    val real: TransformInvocation

    /**
     * 遍历所有输出
     */
    fun traverseAllOutput(init: TraverseListener.() -> Unit)

    /**
     * 删除所有输出
     */
    fun deleteAllOutputProvider()
}