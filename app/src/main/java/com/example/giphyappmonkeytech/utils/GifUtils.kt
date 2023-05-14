package com.example.giphyappmonkeytech.utils

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

//Image loader function for reusability in main and pop up activities

class GifUtils {
    companion object {
        fun getImageLoader(context: Context): ImageLoader {
            return ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
        }
    }
}