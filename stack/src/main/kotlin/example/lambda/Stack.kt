package example.lambda

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.core.Duration
import software.amazon.awscdk.core.Stack as AwsStack
import software.amazon.awscdk.core.StackProps
import software.amazon.awscdk.services.apigateway.LambdaIntegration
import software.amazon.awscdk.services.apigateway.RestApi
import software.amazon.awscdk.services.dynamodb.*
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime

class Stack(
  scope: Construct,
  id: String,
  props: StackProps,
): AwsStack(scope, id, props) {
  init {
    // lambda
    val handler = Function.Builder.create(this, "sample-handler")
      .code(Code.fromAsset("../lambdas/build/libs/lambdas-all.jar"))
      .handler("example.lambda.LambdaHandler")
      .timeout(Duration.seconds(5))
      .memorySize(512)
      .runtime(Runtime.JAVA_8_CORRETTO)
      // .role() // TODO: define a role with AWSLambdaBasicExecutionRole + PutItem on the db
      .build()
    // api gateway
    val api = RestApi.Builder.create(this, "sample-api")
      .restApiName("Sample Service")
      .description("Example api")
      .build()
    val templates = mapOf("application/json" to """{ "statusCode": "200" }""")
    val lambdaIntegration = LambdaIntegration.Builder.create(handler)
      .requestTemplates(templates)
      .build()
    api.root.addResource("handle").addMethod("POST", lambdaIntegration)
    // dynamodb
    val table = Table.Builder.create(this, "sample-table")
      .tableName("sample-table")
      .partitionKey(Attribute.Builder()
        .name("ipaddr") // IPv4 or IPv6 address
        .type(AttributeType.STRING)
        .build()
      )
      .sortKey(Attribute.Builder()
        .name("timestamp") // LocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME)
        .type(AttributeType.STRING)
        .build()
      )
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .build()
  }
}