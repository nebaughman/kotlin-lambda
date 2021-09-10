plugins {
    id("example.lambda.kotlin-application-conventions")
    kotlin("plugin.serialization") version "1.5.20"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-events:3.9.0")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
}

application {
    // TODO: there is no main in this module but shadowJar required one
    mainClass.set("example.lambda.AppKt")
}