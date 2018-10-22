package br.com.acmattos.octo.event

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq

/**
 * Service that deals with MongoDB to persist and retrieve events.
 * @author acmattos
 */
class EventService(private val mongodbUri: String,
                   private val database: String,
                   private val collection: String){
   private val collectionDB: MongoCollection<Event>

   init {
      val client: MongoClient = KMongo.createClient(
         uri = MongoClientURI(mongodbUri))
      val database: MongoDatabase = client.getDatabase(database)
      collectionDB = database.getCollection(collection, Event::class.java)
   }

   /**
    * Saves an event on database.
    * @param event Event.
    */
   fun save(event: Event){
      collectionDB.insertOne(event)
      println("Event saved!")
   }

   /**
    * Finds a list of events based on theirs number.
    * @param number Event's number.
    * @return A list of events.
    */
   fun findByNumber(number: String): List<Event>{
      return collectionDB.find(Event::number eq number)
         .filter { _ -> true }
   }
}