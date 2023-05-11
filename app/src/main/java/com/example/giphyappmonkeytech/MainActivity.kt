package com.example.giphyappmonkeytech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.giphyappmonkeytech.adapters.GifsAdapter
import com.example.giphyappmonkeytech.databinding.ActivityMainBinding
import com.example.giphyappmonkeytech.viewmodels.GifViewModel

class MainActivity : AppCompatActivity() {

    private val gifsViewModel: GifViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        gifsViewModel.gifList.observe(this) {
            (binding.gifRecyclerView.adapter as? GifsAdapter)?.submitList(it)

        }
        gifsViewModel.getGifs()
    }

    private fun setupRecyclerView() {
        binding.gifRecyclerView.apply {
            adapter = GifsAdapter(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}

