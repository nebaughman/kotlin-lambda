package example.lambda

import software.amazon.awscdk.core.App
import software.amazon.awscdk.core.Environment
import software.amazon.awscdk.core.StackProps

fun main() {
  val awsAccount = System.getenv("AWS_ACCOUNT") ?: throw Exception("Missing AWS_ACCOUNT env var")

  val app = App()

  Stack(
    app,
    "sample-stack",
    StackProps.builder()
      .env(
        Environment.builder()
          .account(awsAccount)
          .region("us-east-2")
          .build()
      ).build()
  )

  app.synth()
}