import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    maven
}

group = "info.hellovass.hbox"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven {
        url = uri("https://jitpack.io")
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${getKotlinPluginVersion()}")
}

gradlePlugin {
    plugins {
        create("hbox-gradle-plugin") {
            id = "hbox-gradle-plugin"
            implementationClass = "info.hellovass.hbox.HBoxGradlePlugin"
        }
    }
}