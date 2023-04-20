plugins {
    id("com.android.library").version("8.0.0").apply(false)
    kotlin("multiplatform").version("1.8.20").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
