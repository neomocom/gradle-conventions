package com.neomo.conventions

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

plugins {
    id("com.neomo.conventions.kotlin")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.serverless.gcf)
    implementation(libs.http4k.contract)
    implementation(libs.http4k.jackson)
    implementation(libs.http4k.multipart)
    implementation(libs.http4k.server.jetty)
    testImplementation(libs.http4k.kotest)
}