package com.neomo.conventions

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotestVersion: String by project
val http4kVersion: String by project
val mockkVersion: String by project
val slf4jVersion: String by project
val kotlinLoggingVersion: String by project
val gitCommitSha: String? by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.http4k:http4k-serverless-lambda:$http4kVersion")
    implementation("org.http4k:http4k-contract:$http4kVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("org.slf4j:slf4j-jdk14:$slf4jVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
    testImplementation("org.http4k:http4k-testing-kotest:$http4kVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")

}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        kotlinOptions.allWarningsAsErrors = true
        jvmTarget = "11"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.register<Zip>("buildLambdaZip") {
    archiveClassifier.set(gitCommitSha)
    from(tasks.compileKotlin)
    from(tasks.processResources)
    into("lib") {
        from(configurations.compileClasspath)
    }
}


