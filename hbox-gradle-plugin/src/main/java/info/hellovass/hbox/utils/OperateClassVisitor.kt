package info.hellovass.hbox.utils

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class OperateClassVisitor(api: Int, cv: ClassWriter) : ClassVisitor(api, cv) {

}
