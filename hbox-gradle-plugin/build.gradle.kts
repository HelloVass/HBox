plugins {
    `maven-publish`
    `kotlin-dsl`
}

group = "info.hellovass.hbox"
version = "1.0.0"

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
    implementation(kotlin("stdlib", version = "1.3.72"))
}

// https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:usage
publishing {
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("HBox")
                description.set("HBox is a version plugin")
                url.set("https://github.com/HelloVass/HBox")
                properties.set(
                    mapOf(
                        "author" to "HelloVass"
                    )
                )
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("HelloVass")
                        name.set("HelloVass")
                        email.set("hellova33@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/HelloVass/HBox.git")
                    developerConnection.set("scm:git:ssh://github.com/HelloVass/HBox.git")
                    url.set("https://github.com/HelloVass/HBox")
                }
            }

            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

// https://docs.gradle.org/6.7.1/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
kotlinDslPluginOptions {
    experimentalWarning.set(false)
}