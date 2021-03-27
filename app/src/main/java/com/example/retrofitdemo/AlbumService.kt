package com.example.retrofitdemo

import retrofit2.Response
import retrofit2.http.*

interface AlbumService {
    @GET("/albums")
    suspend fun getAlbum(): Response<Album>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Album>

    @GET("/albums/{id}")
    suspend fun getAlbums(@Path(value = "id") albumId: Int): Response<AlbumItem>

    @POST("/albums")
    suspend fun uploadAlbums(@Body album:AlbumItem) : Response<AlbumItem>
}