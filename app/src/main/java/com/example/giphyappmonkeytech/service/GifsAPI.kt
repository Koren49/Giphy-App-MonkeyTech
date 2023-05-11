package com.example.giphyappmonkeytech.service

import com.example.giphyappmonkeytech.dataclasses.GifDataResult
import com.example.giphyappmonkeytech.utils.Constants.GIPHY_API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsAPI {
    @GET("v1/gifs/trending")
    fun getGifs(
        @Query("api_key") apiKey: String = GIPHY_API_KEY,
        @Query("limit") limit: Int = 20
    ) : Call<GifDataResult>

    @GET("v1/gifs/search")
    fun getGifsByKeyword(
        @Query("api_key") apiKey: String = GIPHY_API_KEY,
        @Query("q") keyword: String,
        @Query("limit") limit: Int = 20,

    ) : Call<GifDataResult>
}