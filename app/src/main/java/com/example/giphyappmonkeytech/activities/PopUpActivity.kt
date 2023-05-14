package com.example.giphyappmonkeytech.activities


import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.giphyappmonkeytech.databinding.PopUpActivityBinding
import com.example.giphyappmonkeytech.dataclasses.GifData
import com.example.giphyappmonkeytech.utils.GifUtils
import com.example.giphyappmonkeytech.viewmodels.GifViewModel


class PopUpActivity : AppCompatActivity() {

    private val gifsViewModel: GifViewModel by viewModels()
    private lateinit var binding: PopUpActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val imageView: ImageView = binding.imageView2
        val gifImage = intent.getSerializableExtra("gif_key") as GifData
        imageView.load(gifImage.images.original.url,imageLoader = GifUtils.getImageLoader(this))

    }
}
