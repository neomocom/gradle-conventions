package com.neomo.conventions

val gitCommitSha: String? by project
val http4kVersion: String by project

plugins {
    id("com.neomo.conventions.kotlin")
}

dependencies {
    implementation("org.http4k:http4k-serverless-lambda:$http4kVersion")
}

tasks.register<Zip>("buildLambdaZip") {
    archiveClassifier.set(gitCommitSha)
    from(tasks.compileKotlin)
    from(tasks.processResources)
    into("lib") {
        from(configurations.compileClasspath)
    }
}


