package com.alesno.testtaskispring.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.response.Video

class VideoViewHolder(private val binding: ItemListMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(video: Video){
        binding.video = video
        binding.executePendingBindings()
    }
}