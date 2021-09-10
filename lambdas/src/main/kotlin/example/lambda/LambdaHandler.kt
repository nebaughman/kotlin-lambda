package example.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LambdaHandler: RequestHandler<Map<String, Any>, String> {

  private val json = Json {}

  override fun handleRequest(input: Map<String, Any>, context: Context): String {
    val message = input["message"]
    context.logger.log("Received message: [$message]")
    return json.encodeToString(mapOf("response" to "Response to [$message]"))
  }
}