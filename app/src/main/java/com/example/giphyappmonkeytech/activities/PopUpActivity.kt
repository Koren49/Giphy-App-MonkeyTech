package com.example.giphyappmonkeytech.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.giphyappmonkeytech.databinding.PopUpActivityBinding
import com.example.giphyappmonkeytech.dataclasses.GifData
import com.example.giphyappmonkeytech.utils.Constants.GIF_KEY
import com.example.giphyappmonkeytech.utils.GifUtils

class PopUpActivity : AppCompatActivity() {

    private lateinit var binding: PopUpActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val gifImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(GIF_KEY, GifData::class.java) ?: return
        } else {
            intent.getSerializableExtra(GIF_KEY) as? GifData ?: return
        }

        binding.imageView2.load(gifImage.images.original.url, imageLoader = GifUtils.getImageLoader(this))
    }
}
