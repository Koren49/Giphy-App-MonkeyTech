package com.example.giphyappmonkeytech.dataclasses

import java.io.Serializable


data class ImageData(
    val original: OriginalImageData
): Serializable

data class OriginalImageData(
    val url: String
): Serializable
