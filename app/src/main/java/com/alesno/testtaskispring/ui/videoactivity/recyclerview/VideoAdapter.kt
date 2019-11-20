package com.alesno.testtaskispring.ui.videoactivity.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.base.BaseRWAdapter
import com.alesno.testtaskispring.databinding.ItemListTopicsBinding

class VideoAdapter: BaseRWAdapter<VideoViewHolder, String>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemListTopicsBinding
                = DataBindingUtil.inflate(inflater, R.layout.item_list_topics, parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(list[position])
    }
}