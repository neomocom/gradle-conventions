package com.neomo.conventions

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

plugins {
    id("com.neomo.conventions.gcf")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.serverless.gcf)
    implementation(libs.http4k.contract)
    testImplementation(libs.http4k.kotest)
}

