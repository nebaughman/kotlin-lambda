plugins {
    id("kotlin.lambda.kotlin-application-conventions")
    kotlin("plugin.serialization") version "1.5.20"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-events:3.9.0")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
}

application {
    // Define the main class for the application.
    //mainClass.set("kotlin.lambda.app.AppKt")
}
