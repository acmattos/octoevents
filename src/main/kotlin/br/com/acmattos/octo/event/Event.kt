package br.com.acmattos.octo.event

/**
 * Data class responsible for holding event data.
 * @author acmattos
 */
data class Event (
   val action: String,
   val repositoryUrl: String,
   val number: String,
   val title: String,
   val login: String,
   val state: String,
   val comments: String,
   val createdAt: String,
   val updatedAt: String,
   val closedAt: String,
   val body: String ){
}