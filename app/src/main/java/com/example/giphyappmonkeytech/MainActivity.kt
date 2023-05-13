package com.example.giphyappmonkeytech

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.widget.ImageView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.giphyappmonkeytech.adapters.GifsAdapter
import com.example.giphyappmonkeytech.databinding.ActivityMainBinding
import com.example.giphyappmonkeytech.viewmodels.GifViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private val gifsViewModel: GifViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playBackgroundMusic()
        setupRecyclerView()
        setupObservers()
    }

    private fun playBackgroundMusic() {
        val intent = Intent(this@MainActivity, BackgroundMusicService::class.java)
        startService(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        val SearchText = searchView.query.toString()

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("",false)
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

    private fun setupObservers() {
        gifsViewModel.gifList.observe(this) {
            (binding.gifRecyclerView.adapter as? GifsAdapter)?.submitList(it)

        }
        gifsViewModel.getGifs()
    }

    private fun setupRecyclerView() {
        binding.gifRecyclerView.adapter = GifsAdapter(this@MainActivity)

        val mediaPlayer : MediaPlayer = MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.isLooping
        mediaPlayer.start()
    }
}

