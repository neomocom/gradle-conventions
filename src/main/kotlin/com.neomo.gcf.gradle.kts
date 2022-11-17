import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


val gcloudInvokerVersion: String by project
val kotestVersion: String by project
val http4kVersion: String by project
val mockkVersion: String by project
val slf4jVersion: String by project
val kotlinLoggingVersion: String by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.github.johnrengelman.shadow")
    id("org.jlleitschuh.gradle.ktlint")
}

val invoker: Configuration by configurations.creating
val systemTest by sourceSets.creating

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.http4k:http4k-serverless-gcf:$http4kVersion")
    implementation("org.http4k:http4k-contract:$http4kVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("org.slf4j:slf4j-api") {
        version {
            strictly("$slf4jVersion")
        }
    }
    implementation("org.slf4j:slf4j-jdk14:$slf4jVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
    testImplementation("org.http4k:http4k-testing-kotest:$http4kVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")

    invoker("com.google.cloud.functions.invoker:java-function-invoker:$gcloudInvokerVersion")
}

configurations[systemTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[systemTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        kotlinOptions.allWarningsAsErrors = true
        jvmTarget = "11"
    }
}

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
    into("../dist/gcf/" + project.name)
    dependsOn(shadowJar)
}

tasks.register<JavaExec>("runFunction") {
    mainClass.set("com.google.cloud.functions.invoker.runner.Invoker")
    dependsOn(shadowJar)
    classpath(invoker)
    inputs.files(configurations.runtimeClasspath.get(), sourceSets.main.get().output)
    args(
        "--target",
        project.findProperty("runFunction.target") ?: "com.learnswell.conversion.ConversionFunction",
        "--port",
        project.findProperty("runFunction.port") ?: 8080
    )
    // Use only shadow jar in order to be as close to an actual deployment as possible
    doFirst {
        args("--classpath", shadowJar.get().outputs.files.singleFile)
    }
}
