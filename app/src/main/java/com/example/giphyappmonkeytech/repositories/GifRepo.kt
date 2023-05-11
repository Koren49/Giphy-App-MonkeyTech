package com.example.giphyappmonkeytech.repositories


import com.example.giphyappmonkeytech.dataclasses.GifDataResult
import com.example.giphyappmonkeytech.service.GiphyRetrofit
import com.example.giphyappmonkeytech.utils.Constants
import retrofit2.Call

// This is what we expose to the caller of the repository (ViewModel usually)
interface GifRepo {
    fun getGifsFromRemote(keyword: String): Call<GifDataResult>
}

// This can include private functions that are not exposed to the caller of the repository (ViewModel usually)
object GifRepoImpl : GifRepo {
    override fun getGifsFromRemote(keyword: String): Call<GifDataResult> {
        return if (keyword.isBlank()) {
            GiphyRetrofit.retroService.getGifs()
        } else {
            GiphyRetrofit.retroService.getGifsByKeyword(Constants.GIPHY_API_KEY,keyword,20)
        }

    }
}


