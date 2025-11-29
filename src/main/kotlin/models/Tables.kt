package com.example.models

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable


object Artists : Table("artists") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val bio = text("bio")
    val imageUrl = varchar("image_url", 255)

    override val primaryKey = PrimaryKey(id)
}

object Albums : Table("albums") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val year = integer("year")
    val coverUrl = varchar("cover_url", 255)
    val artistId = integer("artist_id").references(Artists.id)

    override val primaryKey = PrimaryKey(id)
}

object Songs : Table("songs") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val durationSeconds = integer("duration_seconds")
    val songUrl = varchar("song_url", 255)

    val albumId = integer("album_id").references(Albums.id)

    override val primaryKey = PrimaryKey(id)
}



// DTOs
@Serializable data class Artist(val id: Int, val name: String, val bio: String, val imageUrl: String)
@Serializable data class NewArtist(val name: String, val bio: String, val imageUrl: String)

@Serializable data class Album(val id: Int, val name: String, val year: Int, val coverUrl: String, val artistId: Int)
@Serializable data class NewAlbum(val name: String, val year: Int, val coverUrl: String, val artistId: Int)

@Serializable data class Song(val id: Int, val title: String, val durationSeconds: Int, val songUrl: String, val albumId: Int)
@Serializable data class NewSong(val title: String, val durationSeconds: Int, val songUrl: String, val albumId: Int)