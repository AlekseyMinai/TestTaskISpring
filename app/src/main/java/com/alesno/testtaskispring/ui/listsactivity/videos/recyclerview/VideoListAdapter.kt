package com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.ui.base.BaseRWAdapter
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModel

class VideoListAdapter(val viewModel: CommonViewModel) :
    BaseRWAdapter<VideoListViewHolder, VideoObject>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListMovieBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_list_movie, parent, false)
        return VideoListViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(list[position])
    }


}