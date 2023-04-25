package com.neomo.conventions

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

plugins {
    id("com.neomo.conventions.kotlin")
    id("com.github.johnrengelman.shadow")
}

val invoker: Configuration by configurations.creating
val systemTest: SourceSet by sourceSets.creating

dependencies {
    implementation(libs.slf4j.jdk14)

    invoker(libs.gcloud.function.invoker)
}

configurations[systemTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[systemTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

val shadowJar = tasks.named<ShadowJar>("shadowJar") {
    mergeServiceFiles()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.register<Test>("systemTest") {
    description = "Runs system tests against a specified environment."
    group = "verification"
    useJUnitPlatform()

    testClassesDirs = systemTest.output.classesDirs
    classpath = configurations[systemTest.runtimeClasspathConfigurationName] + configurations.testRuntimeClasspath.get() + systemTest.output
    shouldRunAfter(tasks.test)
}

tasks.register<Sync>("gcfDistribution") {
    from(tasks.getByName<ShadowJar>("shadowJar").outputs.files.singleFile)
    into(project.projectDir.absolutePath + "/dist/gcf/")
    dependsOn(shadowJar)
}

tasks.register<Zip>("gcfPackage") {
    from(tasks.getByName<ShadowJar>("shadowJar").outputs.files.singleFile)
    dependsOn(shadowJar)
}

tasks.register<JavaExec>("runFunction") {
    mainClass.set("com.google.cloud.functions.invoker.runner.Invoker")
    dependsOn(shadowJar)
    classpath(invoker)
    inputs.files(configurations.runtimeClasspath.get(), sourceSets.main.get().output)
    args(
        "--target",
        project.findProperty("runFunction.target")!!,
        "--port",
        project.findProperty("runFunction.port") ?: 8080,
    )
    // Use only shadow jar in order to be as close to an actual deployment as possible
    doFirst {
        args("--classpath", shadowJar.get().outputs.files.singleFile)
        if (!project.hasProperty("runFunction.target")) {
            throw IllegalArgumentException("No runFunction.target property provided")
        }
    }
}
