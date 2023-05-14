package com.example.giphyappmonkeytech.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphyappmonkeytech.dataclasses.GifData
import com.example.giphyappmonkeytech.dataclasses.GifDataResult
import com.example.giphyappmonkeytech.enums.State
import com.example.giphyappmonkeytech.repositories.RepoFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class GifViewModel : ViewModel() {

    // backing field (encapsulation)
    private val _gifList = MutableLiveData<List<GifData>>()
    val gifList: LiveData<List<GifData>> = _gifList

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    // Function that get gifs from retrofit
    fun getGifs(keyword: String = "") {
        CoroutineScope(Dispatchers.IO).launch {
            RepoFactory.gifsRepository.getGifsFromRemote(keyword).enqueue(object :
                retrofit2.Callback<GifDataResult> {
                override fun onResponse(
                    call: Call<GifDataResult>,
                    response: Response<GifDataResult>
                ) {
                    // once we have a response, we can notify the activity with the data
                    if (response.isSuccessful) {
                        // let the state observer know that it is no longer in loading mode, but in idle mode
                        _state.postValue(State.IDLE)
                        val gifsFromSRVS = response.body()

                        // Notify observers with fetched gifs
                        if (gifsFromSRVS != null) {
                            _gifList.postValue(gifsFromSRVS.data)
                        }
                    } else {
                        _state.postValue(State.ERROR)
                    }
                }

                override fun onFailure(call: Call<GifDataResult>, t: Throwable) {
                    _state.postValue(State.ERROR)
                }
            })
        }


    }
}