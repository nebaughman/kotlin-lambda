package example.lambda

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.core.Duration
import software.amazon.awscdk.core.Stack as AwsStack
import software.amazon.awscdk.core.StackProps
import software.amazon.awscdk.services.apigateway.LambdaIntegration
import software.amazon.awscdk.services.apigateway.RestApi
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime

class Stack(
  scope: Construct,
  id: String,
  props: StackProps,
): AwsStack(scope, id, props) {
  init {
    val handler = Function.Builder.create(this, "sample-handler")
      .code(Code.fromAsset("../lambdas/build/libs/lambdas-all.jar"))
      .handler("example.lambda.LambdaHandler")
      .timeout(Duration.seconds(5))
      .memorySize(512)
      .runtime(Runtime.JAVA_8_CORRETTO)
      .build()
    val api = RestApi.Builder.create(this, "sample-api")
      .restApiName("Sample Service")
      .description("Example api")
      .build()
    val templates = mapOf("application/json" to """{ "statusCode": "200" }""")
    val dealIntegration = LambdaIntegration.Builder.create(handler)
      .requestTemplates(templates)
      .build()
    api.root.addResource("handle").addMethod("POST", dealIntegration)
  }
}