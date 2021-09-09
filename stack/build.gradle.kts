plugins {
    id("kotlin.lambda.kotlin-library-conventions")
}

dependencies {
    val cdkVersion = "1.115.0"
    implementation("software.amazon.awscdk:core:${cdkVersion}")
    implementation("software.amazon.awscdk:lambda:${cdkVersion}")
    implementation("software.amazon.awscdk:apigateway:${cdkVersion}")
}