import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.me803159"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.codingchallenge.transactionanalyser.TransactionAnalyserAppKt"
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    // This line of code recursively collects and copies all of a project's files
    // and adds them to the JAR itself. One can extend this task, to skip certain
    // files or particular types at will
    from(configurations.compileClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
}