package com.neomo.conventions

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotestVersion: String by project
val mockkVersion: String by project
val slf4jVersion: String by project
val kotlinLoggingVersion: String by project

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("org.slf4j:slf4j-api") {
        version {
            strictly("$slf4jVersion")
        }
    }
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
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
