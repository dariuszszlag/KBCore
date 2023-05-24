plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("com.dariusz.multiplatform-swiftpackage") version "0.0.16"
}

group = "com.darek"

val GIT_USER: String? by project
val GIT_TOKEN: String? by project
val VERSION_NAME: String? by project

version = VERSION_NAME ?: "0.1"

kotlin {
    android {
        publishLibraryVariants("release")
        publishLibraryVariantsGroupedByFlavor = true
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            isStatic = false
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("io.ktor:ktor-client-core:2.2.3")
                implementation("io.ktor:ktor-client-mock:2.2.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")
                implementation("io.ktor:ktor-client-content-negotiation:2.2.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-android:2.2.3")
            }
        }
        val androidUnitTest by getting {
            dependsOn(commonTest)
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.2.3")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.darek.kbcore"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    publishing {
        multipleVariants {
            withSourcesJar()
            allVariants()
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/dariuszszlag/KBCore")
            credentials {
                username = GIT_USER
                password = GIT_TOKEN
            }
        }
    }
}

multiplatformSwiftPackage {
    packageName("kbcore")
    swiftToolsVersion("5.7")
    outputDirectory(project.rootDir)
    targetPlatforms {
        iOS { v("16") }
    }
    buildConfiguration { release() }
    distributionMode { maven() }
    versionName(VERSION_NAME ?: "0.1")
}
