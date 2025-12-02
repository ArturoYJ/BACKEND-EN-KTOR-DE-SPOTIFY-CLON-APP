package com.example.infrastructure.routes

import com.example.domain.models.*
import com.example.domain.ports.MusicRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json // Necesario para la corrección manual
// import kotlinx.serialization.decodeFromString // A veces necesario dependiendo de la versión, pero Json.decodeFromString funciona generalmente

fun Application.configureRouting(dao: MusicRepository) {
    routing {

        route("/api/artistas") {
            get {
                call.respond(dao.allArtists())
            }
            post {
                try {
                    val form = call.receive<NewArtist>()
                    val created = dao.addNewArtist(form)
                    if (created != null) call.respond(HttpStatusCode.Created, created)
                    else call.respond(HttpStatusCode.BadRequest)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error JSON: ${e.message}")
                }
            }
            get("/{id}") {
                val id = call.parameters["id"]
                val artist = id?.let { dao.artist(it) }
                if (artist != null) call.respond(artist) else call.respond(HttpStatusCode.NotFound)
            }
            delete("/{id}") {
                val id = call.parameters["id"]
                val deleted = id?.let { dao.deleteArtist(it) } ?: false
                if (deleted) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
            }
            put("/{id}") {
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "ID nulo")
                try {
                    val form = call.receive<NewArtist>()
                    val edited = dao.editArtist(id, form)
                    if (edited) call.respond(HttpStatusCode.OK, "Artista actualizado")
                    else call.respond(HttpStatusCode.NotFound, "No existe el artista $id")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error JSON: ${e.message}")
                }
            }
        }

        route("/api/albumes") {
            get { call.respond(dao.allAlbums()) }

            post {
                try {
                    // 1. Recibir texto crudo para interceptar el error de comillas
                    val rawJson = call.receiveText()

                    val fixedJson = rawJson.replace(
                        Regex("\"artistId\":\\s*([a-f0-9\\-]{36})"),
                        "\"artistId\": \"$1\""
                    )

                    val form = Json.decodeFromString<NewAlbum>(fixedJson)

                    val created = dao.addNewAlbum(form)
                    if (created != null) call.respond(HttpStatusCode.Created, created)
                    else call.respond(HttpStatusCode.BadRequest, "ID de artista inválido")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error procesando JSON (Album): ${e.message}")
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]
                if (id != null && dao.deleteAlbum(id)) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
            put("/{id}") {
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
                try {
                    val form = call.receive<NewAlbum>()
                    val edited = dao.editAlbum(id, form)
                    if (edited) call.respond(HttpStatusCode.OK, "Album actualizado")
                    else call.respond(HttpStatusCode.NotFound)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error JSON: ${e.message}")
                }
            }
        }

        route("/api/tracks") {
            get { call.respond(dao.allSongs()) }

            post {
                try {
                    val rawJson = call.receiveText()

                    val fixedJson = rawJson.replace(
                        Regex("\"albumId\":\\s*([a-f0-9\\-]{36})"),
                        "\"albumId\": \"$1\""
                    )

                    val form = Json.decodeFromString<NewSong>(fixedJson)

                    val created = dao.addNewSong(form)
                    if (created != null) call.respond(HttpStatusCode.Created, created)
                    else call.respond(HttpStatusCode.BadRequest, "ID de álbum inválido")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error procesando JSON (Track): ${e.message}")
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]
                if (id != null && dao.deleteSong(id)) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
            put("/{id}") {
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
                try {
                    val form = call.receive<NewSong>()
                    val edited = dao.editSong(id, form)
                    if (edited) call.respond(HttpStatusCode.OK, "Canción actualizada")
                    else call.respond(HttpStatusCode.NotFound)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error JSON: ${e.message}")
                }
            }
        }
    }
}