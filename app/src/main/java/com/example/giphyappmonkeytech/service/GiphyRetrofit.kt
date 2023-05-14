package com.example.giphyappmonkeytech.service

import com.example.giphyappmonkeytech.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Giphy retrofit singleton

object GiphyRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retroService: GifsAPI = retrofit.create(GifsAPI::class.java)
}