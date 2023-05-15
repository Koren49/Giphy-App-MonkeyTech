package com.example.giphyappmonkeytech

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.giphyappmonkeytech.databinding.GifLayoutBinding
import com.example.giphyappmonkeytech.dataclasses.GifData
import com.example.giphyappmonkeytech.utils.GifUtils.Companion.getImageLoader

class GifsAdapter(val context: Context, private val onGifClickedListener: OnGifClickedListener) : ListAdapter<GifData, GifsAdapter.GifsViewHolder>(DiffCallback()) {

    class GifsViewHolder(val binding: GifLayoutBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding = GifLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val data = getItem(position)
        with(holder) {
            binding.gifTitle.text= data.title
            binding.ivGif.load(data.images.original.url,imageLoader = getImageLoader(context))

            binding.shareButton.setOnClickListener(View.OnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("image/gif")
                val body = "Share this gif"
                val sub = data.images.original.url
                intent.putExtra(Intent.EXTRA_TEXT, body)
                intent.putExtra(Intent.EXTRA_TEXT, sub)
                context.startActivity(Intent.createChooser(intent,"Share using"))

            })

            holder.binding.rootLayout.setOnClickListener {
                onGifClickedListener.onGifClicked(data)

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

interface OnGifClickedListener {
    fun onGifClicked(data:GifData){}
}