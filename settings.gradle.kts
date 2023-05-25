pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/dariuszszlag/multiplatform-swiftpackage")
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
        maven {
            url = uri("https://maven.pkg.github.com/dariuszszlag/multiplatform-swiftpackage")
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

rootProject.name = "KBCore"
include(":kbcore")