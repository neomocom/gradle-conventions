package com.neomo.conventions

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

repositories {
    mavenCentral()
    mavenLocal()
}

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
    version.set(libs.versions.ktlint.core.get())
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.slf4j.api) {
        version {
            strictly(libs.versions.slf4j.get())
        }
    }
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)
    testImplementation(libs.mockk)
}

kotlin {
    jvmToolchain(11)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        kotlinOptions.allWarningsAsErrors = true
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
