package com.example.domain.ports

import com.example.dto.Album
import com.example.dto.Artist
import com.example.dto.NewAlbum
import com.example.dto.NewArtist
import com.example.dto.NewSong
import com.example.dto.Song

interface DAOFacade {

        suspend fun allArtists(): List<Artist>
        suspend fun artist(id: Int): Artist?
        suspend fun addNewArtist(artist: NewArtist): Artist?
        suspend fun deleteArtist(id: Int): Boolean
        suspend fun editArtist(id: Int, artist: NewArtist): Boolean

        suspend fun allAlbums(): List<Album>
        suspend fun albumsByArtist(artistId: Int): List<Album>
        suspend fun addNewAlbum(album: NewAlbum): Album?
        suspend fun deleteAlbum(id: Int): Boolean
        suspend fun editAlbum(id: Int, album: NewAlbum): Boolean

        suspend fun allSongs(): List<Song>
        suspend fun songsByAlbum(albumId: Int): List<Song>
        suspend fun addNewSong(song: NewSong): Song?
        suspend fun deleteSong(id: Int): Boolean
        suspend fun editSong(id: Int, song: NewSong): Boolean
    }