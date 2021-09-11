package example.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LambdaHandler: RequestHandler<Map<String, Any>, APIGatewayProxyResponseEvent> {

  private val json = Json {}

  override fun handleRequest(input: Map<String, Any>, context: Context): APIGatewayProxyResponseEvent {
    val body = json.decodeFromString<Map<String,String>>(input["body"] as String)
    val message = body["message"]
    context.logger.log("Received message: [$message]")
    val responseBody = json.encodeToString(mapOf("response" to "Response to [$message]"))
    return APIGatewayProxyResponseEvent()
      .withStatusCode(200)
      .withIsBase64Encoded(false)
      .withBody(responseBody)
  }
}