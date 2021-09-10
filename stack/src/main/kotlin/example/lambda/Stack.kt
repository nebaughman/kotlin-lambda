package example.lambda

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.core.Duration
import software.amazon.awscdk.core.Stack as AwsStack
import software.amazon.awscdk.core.StackProps
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime

class Stack(
  scope: Construct,
  id: String,
  props: StackProps,
): AwsStack(scope, id, props) {
  init {
    Function.Builder.create(this, "sample-handler")
      .code(Code.fromAsset("../lambdas/build/libs/lambdas-all.jar"))
      .handler("example.lambda.LambdaHandler")
      .timeout(Duration.seconds(5))
      .memorySize(1024)
      .runtime(Runtime.JAVA_8_CORRETTO)
      .build()
  }
}