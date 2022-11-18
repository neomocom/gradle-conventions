plugins {
    `kotlin-dsl`
    kotlin("jvm") version embeddedKotlinVersion
    `maven-publish`
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = "com.neomo.conventions"
version = "0.1"

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

pluginBundle {
    website = "https://github.com/neomocom/gradle-conventions"
    vcsUrl = "https://github.com/neomocom/gradle-conventions"
    tags = listOf("convention", "kotlin")
}

gradlePlugin {
    plugins {
        named("com.neomo.conventions.gcf").configure {
            displayName = "NEOMO GCF conventions"
            description = "NEOMO conventions for google cloud functions written in kotlin"
        }
    }
}
