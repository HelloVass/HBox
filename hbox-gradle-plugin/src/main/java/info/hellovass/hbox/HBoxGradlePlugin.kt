package info.hellovass.hbox

import org.gradle.api.Plugin
import org.gradle.api.Project

class HBoxGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {

        GradleLogger.initialize(target.gradle)

        GradleLogger.success("apply hbox-gradle-plugin")
    }
}