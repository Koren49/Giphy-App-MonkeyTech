package com.example.giphyappmonkeytech.dataclasses

import java.io.Serializable



data class GifData (

    val title: String,
    val images: ImageData
) : Serializable
