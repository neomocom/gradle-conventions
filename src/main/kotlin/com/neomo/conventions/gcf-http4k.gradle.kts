package com.neomo.conventions

val http4kVersion: String by project

plugins {
    id("com.neomo.conventions.gcf")
}

dependencies {
    implementation("org.http4k:http4k-serverless-gcf:$http4kVersion")
    implementation("org.http4k:http4k-contract:$http4kVersion")

    testImplementation("org.http4k:http4k-testing-kotest:$http4kVersion")
}

