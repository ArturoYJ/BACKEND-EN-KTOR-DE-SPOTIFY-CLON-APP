package com.example.domain.models

import org.jetbrains.exposed.sql.Table


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