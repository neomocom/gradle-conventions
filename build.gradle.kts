
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
version = "0.10.2"

val javaVersion = JavaLanguageVersion.of(17)

java {
    toolchain {
        languageVersion = javaVersion
    }
}

kotlin {
    jvmToolchain {
        languageVersion = javaVersion
    }
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

gradlePlugin {
    website = "https://github.com/neomocom/gradle-conventions"
    vcsUrl = "https://github.com/neomocom/gradle-conventions"
    plugins {
        getByName("com.neomo.conventions.gcf") {
            displayName = "NEOMO GCF conventions"
            description = "NEOMO conventions for google cloud functions written in kotlin"
            tags = listOf("convention", "kotlin", "gcloud")
        }

        getByName("com.neomo.conventions.gcf-http4k") {
            displayName = "NEOMO GCF http4k conventions"
            description = "NEOMO conventions for google cloud functions written in kotlin with http interface"
            tags = listOf("convention", "kotlin", "gcloud", "http4k")
        }

        getByName("com.neomo.conventions.lambda") {
            displayName = "NEOMO AWS lambda conventions"
            description = "NEOMO conventions for lambda functions written in kotlin"
            tags = listOf("convention", "kotlin", "aws", "lambda")
        }

        getByName("com.neomo.conventions.http4k") {
            displayName = "NEOMO http4k conventions"
            description = "NEOMO conventions for http4k (kotlin)"
            tags = listOf("convention", "kotlin", "http4k")
        }

        getByName("com.neomo.conventions.kotlin") {
            displayName = "NEOMO kotlin conventions"
            description = "NEOMO conventions for kotlin (ktlint, kotest, mockk)"
            tags = listOf("convention", "kotlin")
        }
    }
}
