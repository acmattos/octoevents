package br.com.acmattos.octo.event

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException

/**
 * Deserializes GithHub's issues event.
 * @author acmattos
 */
class EventDeserializer(vc: Class<*>?) : StdDeserializer<Event>(vc) {
    /**
     * Deserializes GithHub's issues event to Octo Events event.
     *
     * @param jp JSON parser.
     * @param ctxt Deserialization Context.
     */
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Event? {
        val jsonNode = jp.codec.readTree<JsonNode>(jp)
        var event: Event? = null
        if (jsonNode.has("issue")) {
            event = Event(
                jsonNode.get("action").asText(),
                jsonNode.get("issue").get("repository_url").asText(),
                jsonNode.get("issue").get("number").asText(),
                jsonNode.get("issue").get("title").asText(),
                jsonNode.get("issue").get("user").get("login").asText(),
                jsonNode.get("issue").get("state").asText(),
                jsonNode.get("issue").get("comments").asText(),
                jsonNode.get("issue").get("created_at").asText(),
                jsonNode.get("issue").get("updated_at").asText(),
                jsonNode.get("issue").get("closed_at").asText(),
                jsonNode.get("issue").get("body").asText()
            )
        }
        return event
    }
}