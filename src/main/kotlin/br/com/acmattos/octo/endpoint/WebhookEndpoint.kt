package br.com.acmattos.octo.endpoint

import br.com.acmattos.octo.event.Event
import br.com.acmattos.octo.event.EventDeserializer
import br.com.acmattos.octo.event.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.javalin.Context

/**
 * Deals with GitHub's event data processed by this application.
 *
 * @author acmattos
 * @since 1.0.0
 * @property service Service that deals with MongoDB.
 * @constructor Creates a Webhook Endpoint.
 */
class WebhookEndpoint(private val service: EventService) {
    /**
     * Process HTTP POST requests for http://host:port/webhooks
     * Processes GitHub's event data sent to this resource.
     *
     * @param ctx Provides access to functions for handling the request and
     *            response.
     */
    fun post(ctx: Context) {
        var status: Int
        try {
            var payload: String = ctx.body()
            val event = objectMapper().readValue(payload, Event::class.java)
            if (event == null) {
                status = 400
                println("Event could not be persisted on DB: ${payload}")
            } else {
                service.save(event)
                status = 201
                println("Event persisted on DB: ${event}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            status = 500
        }
        ctx.status(status)
    }

    /**
     * Builds and configures ObjectMapper accordingly.
     * @return ObjectMapper.
     */
    private fun objectMapper(): ObjectMapper {
        val module = SimpleModule()
        module.addDeserializer(Event::class.java, EventDeserializer(Event::class.java))
        val mapper = ObjectMapper()
        mapper.registerModule(module)
        return mapper
    }
}