pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/dariuszszlag/KBCore")
            credentials {
                username = "dariuszszlag"
                password = System.getenv("ACCESS_TOKEN")
            }
        }
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KBCore"
include(":kbcore")