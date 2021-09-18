package example.lambda

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LambdaHandler: RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private val json = Json {
    prettyPrint = true // TODO: not for production
  }

  override fun handleRequest(
    event: APIGatewayProxyRequestEvent,
    context: Context
  ): APIGatewayProxyResponseEvent {
    val logger = context.logger
    writeDb(event.requestContext, logger)
    val body = json.decodeFromString<Map<String,String>>(event.body)
    logger.log(body.toString())
    val message = body["message"]
    logger.log("Received message: [$message]")
    val responseBody = json.encodeToString(mapOf("response" to "Response to [$message]"))
    return APIGatewayProxyResponseEvent()
      .withStatusCode(200)
      .withIsBase64Encoded(false)
      .withBody(responseBody)
  }

  private fun writeDb(
    context: APIGatewayProxyRequestEvent.ProxyRequestContext,
    logger: LambdaLogger,
  ) = runBlocking {
    val ipaddr = context.identity.sourceIp // partition key
    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    val userAgent = context.identity.userAgent
    val itemMap = mapOf(
      "ipaddr" to AttributeValue.S(ipaddr),
      "timestamp" to AttributeValue.S(timestamp),
      "userAgent" to AttributeValue.S(userAgent),
    )
    val db = DynamoDbClient { region = "us-east-2" } // TODO: configurable region
    val response = try {
      db.putItem {
        tableName = "sample-table"
        item = itemMap
      }
    } finally {
      db.close() // should have been Closeable
    }
    logger.log(response.toString())
  }
}