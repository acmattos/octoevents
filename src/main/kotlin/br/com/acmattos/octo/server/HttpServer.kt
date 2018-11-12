package br.com.acmattos.octo.server

import br.com.acmattos.octo.endpoint.IssueEndpoint
import br.com.acmattos.octo.endpoint.WebhookEndpoint
import io.javalin.Javalin

/**
 * HTTP Server that listen to REST requests.
 * @author acmattos
 */
class HttpServer(private val port: Int,
                 private val webhookEndpoint: WebhookEndpoint,
                 private val issueEndpoint: IssueEndpoint) {
   private val app: Javalin

   init {
      app = Javalin.create().apply {
         port(port)
         exception(Exception::class.java) { e, _ -> e.printStackTrace() }
      }.requestLogger { ctx, timeMs ->
         println("${ctx.method()} ${ctx.path()} took $timeMs ms")
      }
   }

   /**
    * Starts this server.
    */
   fun start() {
      app.start()
      app.post("webhooks") { ctx -> webhookEndpoint.post(ctx) }
      app.get("/issues/:number/events") { ctx -> issueEndpoint.get(ctx) }
   }

   /**
    * Stops this server.
    */
   fun stop() {
      app.stop()
   }
}