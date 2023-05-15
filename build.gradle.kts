
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    kotlin("jvm") version embeddedKotlinVersion
    `maven-publish`
    id("java-gradle-plugin")
    alias(libs.plugins.versions)
    alias(libs.plugins.publish)
}

group = "com.neomo.conventions"
version = "0.8.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

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
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.gradle.serialization)
    implementation(libs.shadow.gradle)
    implementation(libs.ktlint.gradle)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

}

pluginBundle {
    website = "https://github.com/neomocom/gradle-conventions"
    vcsUrl = "https://github.com/neomocom/gradle-conventions"
    tags = listOf("convention", "kotlin")
}

gradlePlugin {
    plugins {
        getByName("com.neomo.conventions.gcf") {
            displayName = "NEOMO GCF conventions"
            description = "NEOMO conventions for google cloud functions written in kotlin"
        }

        getByName("com.neomo.conventions.gcf-http4k") {
            displayName = "NEOMO GCF conventions"
            description = "NEOMO conventions for google cloud functions written in kotlin with http interface"
        }

        getByName("com.neomo.conventions.lambda") {
            displayName = "NEOMO AWS lambda conventions"
            description = "NEOMO conventions for lambda functions written in kotlin"
        }

        getByName("com.neomo.conventions.kotlin") {
            displayName = "NEOMO kotlin conventions"
            description = "NEOMO conventions for kotlin (ktlint, kotest, mockk)"
        }
    }
}
