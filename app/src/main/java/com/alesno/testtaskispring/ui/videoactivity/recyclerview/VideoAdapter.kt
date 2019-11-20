package com.alesno.testtaskispring.ui.videoactivity.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.ItemListTopicsBinding

class VideoAdapter: RecyclerView.Adapter<VideoViewHolder>() {

    var topics: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemListTopicsBinding
                = DataBindingUtil.inflate(inflater, R.layout.item_list_topics, parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}