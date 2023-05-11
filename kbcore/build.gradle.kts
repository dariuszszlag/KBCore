plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("co.touchlab.faktory.kmmbridge") version "0.3.7"
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

kmmbridge {
    mavenPublishArtifacts()
    generateVersionName()
    spm()
}

tasks.withType<PublishToMavenRepository> {
    dependsOn(tasks.assemble)
}

fun co.touchlab.faktory.KmmBridgeExtension.generateVersionName() {
    versionManager.apply {
        set(object: co.touchlab.faktory.versionmanager.VersionManager {
            override val needsGitTags: Boolean
                get() = true

            override fun getVersion(
                project: Project,
                versionPrefix: String,
                versionWriter: co.touchlab.faktory.versionmanager.VersionWriter
            ): String = project.version.toString()

        })
        finalizeValue()
    }
    versionWriter.apply {
        set(
            object: co.touchlab.faktory.versionmanager.VersionWriter {
                override fun initVersions(project: Project) {
                    try {
                        project.procRunFailThrow("git", "fetch", "--all", "--tags")
                        project.procRunFailThrow("git", "checkout", System.getenv("BRANCH_NAME"))
                    } catch (e: co.touchlab.faktory.internal.ProcOutputException) {
                        val localOk = e.output.any { it.contains("There is no tracking information for the current branch") }
                        throw co.touchlab.faktory.versionmanager.VersionException(
                            localOk, if (localOk) {
                                "Version cannot be loaded. Publishing disabled (this is fine for local development)"
                            } else {
                                "${e.message}\n${e.output.joinToString("\n")}"
                            }
                        )
                    }
                }

                override fun scanVersions(project: Project, block: (Sequence<String>) -> Unit) {
                    procRunSequence("git", "tag", block = block)
                }

                override fun writeMarkerVersion(project: Project, version: String) {
                    val correctVersion = project.setProperVersion(version)
                    project.procRunFailThrow("git", "tag", correctVersion)
                    project.procRunFailThrow("git", "push", "--tags")
                }

                override fun cleanMarkerVersions(project: Project, filter: (String) -> Boolean) {
                    procRunSequence("git", "tag") { sequence ->
                        val partialVersionSequence = sequence.filter(filter)
                        partialVersionSequence.forEach { tag ->
                            project.logger.warn("Deleting tag $tag")
                            project.procRunFailThrow("git", "tag", "-d", tag)
                            project.procRunFailThrow("git", "push", "origin", "-d", tag)
                        }
                    }
                }

                override fun writeFinalVersion(project: Project, version: String) {
                    val correctVersion = project.setProperVersion(version)
                    project.procRunFailLog("git", "tag", "-a", correctVersion, "-m", "KMM release version $correctVersion")
                    project.procRunFailLog("git", "push", "--follow-tags")
                }

                override fun runGitStatement(project: Project, vararg params: String) {
                    project.procRunFailLog("git", *params)
                }
            }
        )
        finalizeValue()
    }
}

fun procRun(vararg params: String, processLines: (String, Int) -> Unit): Unit {
    val process = ProcessBuilder(*params)
        .redirectErrorStream(true)
        .start()

    val streamReader = process.inputStream.reader()
    val bufferedReader = streamReader.buffered()
    var lineCount = 1

    bufferedReader.forEachLine { line ->
        processLines(line, lineCount)
        lineCount++
    }

    bufferedReader.close()
    val returnValue = process.waitFor()
    if(returnValue != 0)
        throw GradleException("Process failed: ${params.joinToString(" ")}")
}
fun procRunSequence(vararg params: String, block:(Sequence<String>)->Unit) {
    val process = ProcessBuilder(*params)
        .redirectErrorStream(true)
        .start()

    val streamReader = process.inputStream.reader()
    val bufferedReader = streamReader.buffered()

    var thrown:Throwable? = null

    try {
        block(bufferedReader.lineSequence())
    } catch (e: Throwable) {
        thrown = e
    }

    bufferedReader.close()
    val returnValue = process.waitFor()
    if(returnValue != 0)
        throw GradleException("Process failed: ${params.joinToString(" ")}", thrown)

    if(thrown != null){
        throw thrown
    }
}

/**
 * Run a process. If it fails, write output to gradle error log and throw exception.
 */
fun Project.procRunFailLog(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        output.forEach { logger.error("error: $it") }
        throw e
    }
    return output
}

/**
 * Run a process. If it fails, write output to gradle warn log and return an empty list.
 */
fun Project.procRunWarnLog(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        output.forEach { logger.warn("warn: $it") }
        return emptyList()
    }
    return output
}

/**
 * Run a process. If it fails, write output to gradle error log and throw exception.
 */
fun Project.procRunFailThrow(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        throw ProcOutputException("Project.procRunFailLog [failed]: ${params.joinToString(" ")}", output)
    }
    return output
}

fun Project.setProperVersion(version: String): String {
    val versionNumberRegex = Regex("([0-9]+(\\.[0-9]+)+)")
    return version.replace(versionNumberRegex, this.version.toString())
}

class ProcOutputException(message: String?, val output: List<String>) : Exception(message)
