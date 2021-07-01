package info.hellovass.hbox

import org.gradle.api.invocation.Gradle
import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutputFactory
import org.gradle.kotlin.dsl.support.serviceOf

object GradleLogger {

    private var out: StyledTextOutput? = null

    fun initialize(gradle: Gradle) {
        out = gradle.serviceOf<StyledTextOutputFactory>().create("hbox-gradle-plugin-output")
    }

    fun normal(message: String) {
        out?.style(StyledTextOutput.Style.Normal)?.run {
            println(message)
        }
    }

    fun header(message: String) {
        out?.style(StyledTextOutput.Style.Header)?.run {
            println(message)
        }
    }

    fun userInput(message: String) {
        out?.style(StyledTextOutput.Style.UserInput)?.run {
            println(message)
        }
    }

    fun identifier(message: String) {
        out?.style(StyledTextOutput.Style.Identifier)?.run {
            println(message)
        }
    }

    fun description(message: String) {
        out?.style(StyledTextOutput.Style.Description)?.run {
            println(message)
        }
    }

    fun progressStatus(message: String) {
        out?.style(StyledTextOutput.Style.ProgressStatus)?.run {
            println(message)
        }
    }

    fun success(message: String) {
        out?.style(StyledTextOutput.Style.Success)?.run {
            println(message)
        }
    }

    fun successHeader(message: String) {
        out?.style(StyledTextOutput.Style.SuccessHeader)?.run {
            println(message)
        }
    }

    fun failure(message: String) {
        out?.style(StyledTextOutput.Style.Failure)?.run {
            println(message)
        }
    }

    fun failureHeader(message: String) {
        out?.style(StyledTextOutput.Style.FailureHeader)?.run {
            println(message)
        }
    }

    fun info(message: String) {
        out?.style(StyledTextOutput.Style.Info)?.run {
            println(message)
        }
    }

    fun error(message: String) {
        out?.style(StyledTextOutput.Style.Error)?.run {
            println(message)
        }
    }
}