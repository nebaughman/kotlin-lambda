package example.lambda

import software.amazon.awscdk.core.App
import software.amazon.awscdk.core.StackProps

fun main() {

  val app = App()

  Stack(
    app,
    "sample-stack",
    StackProps.builder()
      /* environment can be obtained from standard ~/.aws config
      .env(
        Environment.builder()
          .account(awsAccount)
          .region("us-east-2")
          .build()
      )
      */
      .build()
  )

  app.synth()
}