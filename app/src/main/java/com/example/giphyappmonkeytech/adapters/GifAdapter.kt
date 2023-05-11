package com.example.giphyappmonkeytech.adapters

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.ImageLoader.*
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.bumptech.glide.Glide
import com.example.giphyappmonkeytech.GlideApp
import com.example.giphyappmonkeytech.databinding.GifLayoutBinding
import com.example.giphyappmonkeytech.dataclasses.GifData
import com.example.giphyappmonkeytech.dataclasses.GifDataResult

class GifsAdapter(val context: Context, val gifs: List<GifData> = mutableListOf()) : ListAdapter<GifData, GifsAdapter.GifsViewHolder>(DiffCallback()) {

    class GifsViewHolder(val binding: GifLayoutBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding = GifLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val data = getItem(position)
        val imageLoader = Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        with(holder) {
            with(data) {
                binding.gifTitle.text= this.title
                binding.ivGif.load(data.bitly_gif_url,imageLoader = imageLoader)

            }
        }



    }

    // Skip unchanged items in the RecyclerView
    class DiffCallback : DiffUtil.ItemCallback<GifData>(){
        override fun areItemsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return oldItem.title == newItem.title
        }
        
        override fun areContentsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return oldItem == newItem
        }
    }
}