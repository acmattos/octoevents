package br.com.acmattos.octo.server

import br.com.acmattos.octo.endpoint.IssueEndpoint
import br.com.acmattos.octo.endpoint.WebhookEndpoint
import io.javalin.Javalin

/**
 * HTTP Server that listen to REST requests.
 *
 * @author acmattos
 * @since 1.0.0
 * @property port Port that this HTTP Server will listen to.
 * @property issueEndpoint Issue Endpoint.
 * @property webhookEndpoint Webhook Endpoint.
 * @constructor Creates a HTTP Server.
 */
class HttpServer(
    private val port: Int = 7000,
    private val issueEndpoint: IssueEndpoint,
    private val webhookEndpoint: WebhookEndpoint
) {
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