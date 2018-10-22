package br.com.acmattos.octo.endpoint

import br.com.acmattos.octo.event.EventService
import br.com.acmattos.octo.kodein
import io.javalin.Context
import org.kodein.di.generic.instance

/**
 * Deals with Issues' event data stored by this application.
 * @author acmattos
 */
class IssueEndpoint() {
   private val service: EventService by kodein.instance()

   /**
    * Process HTTP GET requests for http://host:port/issues/<NUMBER>/events
    * Finds issues by its NUMBER.
    *
    * @param ctx Provides access to functions for handling the request and
    *            response.
    */
   fun get(ctx: Context) {
      var status: Int
      try {
         val events = service.findByNumber(ctx.pathParam("number"))
         if(events.isEmpty()){
            status = 404
            println("No events found for number: ${ctx.pathParam("number")}")
         } else {
            ctx.json(events)
            println("Events captured: ${events.size}")
            status = 200
         }
      } catch (e: Exception){
         e.printStackTrace()
         status = 500
      }
      ctx.status(status)
   }
}