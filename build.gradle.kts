plugins {
    `kotlin-dsl`
    kotlin("jvm") version embeddedKotlinVersion
    `maven-publish`
}

group = "com.neomo.conventions"
version = "1.0"

val kotlinVersion = "1.7.20"
val shadowVersion = "7.1.2"
val ktlintVersion = "11.0.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

publishing {
    repositories {
        mavenLocal()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin.plugin.serialization:org.jetbrains.kotlin.plugin.serialization.gradle.plugin:$kotlinVersion")
    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:$shadowVersion")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion")
}
