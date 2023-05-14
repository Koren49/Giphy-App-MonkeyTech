package com.example.giphyappmonkeytech.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.giphyappmonkeytech.GifsAdapter
import com.example.giphyappmonkeytech.OnGifClickedListener
import com.example.giphyappmonkeytech.R
import com.example.giphyappmonkeytech.databinding.ActivityMainBinding
import com.example.giphyappmonkeytech.databinding.PopUpActivityBinding
import com.example.giphyappmonkeytech.dataclasses.GifData
import com.example.giphyappmonkeytech.service.BackgroundMusicService
import com.example.giphyappmonkeytech.viewmodels.GifViewModel



class MainActivity : AppCompatActivity(), OnGifClickedListener {

    private lateinit var toolbar: Toolbar

    private val gifsViewModel: GifViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playBackgroundMusic()
        setupRecyclerView()
        setupObservers()



//            val gifImage = findViewById<ImageView>(R.id.iv_gif)
//            gifImage.setOnClickListener(){
//                val myIntent = Intent(this@MainActivity, PopUpActivity::class.java)
//                myIntent.putExtra("gif_key",gifImage )
//                startActivity(myIntent)
//        }

    }

    private fun playBackgroundMusic() {
        val intent = Intent(this@MainActivity, BackgroundMusicService::class.java)
        startService(intent)
    }

    // Search from toolbar and filter from retrofit
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView


        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                gifsViewModel.getGifs("$query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    // Sets up the information in the recyclerView
    private fun setupObservers() {
        gifsViewModel.gifList.observe(this) {
            (binding.gifRecyclerView.adapter as? GifsAdapter)?.submitList(it)

        }
        gifsViewModel.getGifs()
    }

    // Sets up the recyclerView
    private fun setupRecyclerView() {
        binding.gifRecyclerView.adapter = GifsAdapter(this@MainActivity,this )

    }

    override fun onGifClicked(gifData: GifData) {
        val myIntent = Intent(this@MainActivity, PopUpActivity::class.java)
        myIntent.putExtra("gif_key", gifData.images.original.url)
        val gifData: GifData = intent.getSerializableExtra("gif_key") as GifData
        startActivity(myIntent)
    }
}

