package info.hellovass.hbox

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import info.hellovass.hbox.utils.GradleLogger
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

class HBoxGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        GradleLogger.initialize(project.gradle)

        GradleLogger.success("apply hbox-gradle-plugin")

        config(project.plugins, project)
    }

    private fun config(plugins: PluginContainer, project: Project) {
        plugins.whenPluginAdded {
            when (this) {
                is AppPlugin -> {
                    GradleLogger.success("com.android.application plugin is add")
                }
                is LibraryPlugin -> {
                    GradleLogger.success("com.android.library plugin is add")
                }
                is KotlinAndroidPluginWrapper -> {

                }
            }
        }
    }
}