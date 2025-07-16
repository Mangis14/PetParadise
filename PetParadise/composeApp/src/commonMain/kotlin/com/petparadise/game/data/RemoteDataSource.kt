package com.petparadise.game.data

import com.petparadise.game.api.RemoteApi

class RemoteDataSource(
    private val remoteApi: RemoteApi
) {
    suspend fun getAlbums() = remoteApi.getAlbums()
}