package com.example.giphyappmonkeytech.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import coil.load
import com.example.giphyappmonkeytech.R
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

        val imageView: ImageView = binding.imageView2
        val bundle: Bundle = (intent.extras?: finish()) as Bundle
        val resId: Int = bundle.getInt("resId")
        val gifImage = intent.getSerializableExtra("gif_key") as GifData
        imageView.load(gifImage.images.original.url,imageLoader = GifUtils.getImageLoader(this))
//      imageView.setImageResource(resId)
    }
}