package com.alesno.testtaskispring.ui.videoactivity.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListTopicsBinding

class VideoViewHolder(val binding: ItemListTopicsBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(topic: String){
        binding.topic = topic
        binding.executePendingBindings()
    }
}