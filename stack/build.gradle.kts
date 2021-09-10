plugins {
    id("example.lambda.kotlin-application-conventions")
    id("example.lambda.kotlin-library-conventions")
}

dependencies {
    val cdkVersion = "1.115.0"
    implementation("software.amazon.awscdk:core:${cdkVersion}")
    implementation("software.amazon.awscdk:lambda:${cdkVersion}")
    implementation("software.amazon.awscdk:apigateway:${cdkVersion}")
}

application {
    mainClass.set("example.lambda.AppKt")
}