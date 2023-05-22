pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven ("https://s01.oss.sonatype.org/content/repositories/releases/")
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