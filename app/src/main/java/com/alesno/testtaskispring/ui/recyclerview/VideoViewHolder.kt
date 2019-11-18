package com.alesno.testtaskispring.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

class VideoViewHolder(val binding: ItemListMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(video: VideoObject){
        binding.video = video
        binding.executePendingBindings()
    }
}