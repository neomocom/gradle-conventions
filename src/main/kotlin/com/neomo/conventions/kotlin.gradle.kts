package com.neomo.conventions

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.gradleup.shadow")
}

ktlint {
    version.set(libs.versions.ktlint.core.get())
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
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

tasks.named<ShadowJar>("shadowJar") {
    mergeServiceFiles()
    manifest {
        attributes["Multi-Release"] = true
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
