package br.com.acmattos.octo

import br.com.acmattos.octo.endpoint.IssueEndpoint
import br.com.acmattos.octo.endpoint.WebhookEndpoint
import br.com.acmattos.octo.event.EventService
import br.com.acmattos.octo.server.HttpServer
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Bootstraps the application and sets up the dependency injection.
 *
 * @author acmattos
 * @since 1.0.0
 */
val kodein = Kodein {
    val config = ConfigurationProperties.fromResource(
        "application.properties"
    )
    val port = config[Key("octo.server.port", intType)]
    val uri = config[Key("octo.mongodb.uri", stringType)]
    val database = config[Key("octo.mongodb.database", stringType)]
    val collection = config[Key("octo.mongodb.collection", stringType)]

    bind<HttpServer>() with singleton { HttpServer(port, instance(), instance()) }
    bind<WebhookEndpoint>() with singleton { WebhookEndpoint(instance()) }
    bind<IssueEndpoint>() with singleton { IssueEndpoint(instance()) }
    bind<EventService>() with singleton {
        EventService(
            uri, database, collection
        )
    }
}

/**
 * Bootstraps this application.
 * @param args not used.
 */
fun main(args: Array<String>) {
    val httpServer: HttpServer by kodein.instance()
    httpServer.start()
}

