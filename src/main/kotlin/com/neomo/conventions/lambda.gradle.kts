package com.neomo.conventions

val gitCommitSha: String? by project

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

plugins {
    id("com.neomo.conventions.kotlin")
}

dependencies {
    implementation(libs.http4k.serverless.lambda)
}

tasks.register<Zip>("buildLambdaZip") {
    archiveClassifier.set(gitCommitSha)
    from(tasks.compileKotlin)
    from(tasks.processResources)
    into("lib") {
        from(configurations.compileClasspath)
    }
}


