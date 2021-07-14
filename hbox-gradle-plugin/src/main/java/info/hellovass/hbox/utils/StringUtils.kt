package info.hellovass.hbox.utils

import org.codehaus.groovy.runtime.StringGroovyMethods

object StringUtils {
    operator fun CharSequence.minus(target: String): String = StringGroovyMethods.minus(
        this,
        target
    )
}

