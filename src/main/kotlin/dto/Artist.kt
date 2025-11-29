package com.example.dto

import kotlinx.serialization.Serializable

@Serializable data class Artist(
    val id: Int,
    val name: String,
    val bio: String,
    val imageUrl: String
)

@Serializable data class NewArtist(
    val name: String,
    val bio: String,
    val imageUrl: String
)

@Serializable data class Album(
    val id: Int,
    val name: String,
    val year: Int,
    val coverUrl: String,
    val artistId: Int
)

@Serializable data class NewAlbum(
    val name: String,
    val year: Int,
    val coverUrl:String,
    val artistId: Int
)

@Serializable data class Song(
    val id: Int,
    val title: String,
    val durationSeconds: Int,
    val songUrl: String,
    val albumId: Int
)

@Serializable data class NewSong(
    val title: String,
    val durationSeconds:Int,
    val songUrl: String,
    val albumId: Int
)