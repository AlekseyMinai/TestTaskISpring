package com.alesno.testtaskispring.ui.videoactivity.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.ui.base.BaseRWAdapter
import com.alesno.testtaskispring.databinding.ItemListTopicsBinding

class TopicsAdapter: BaseRWAdapter<TopicsViewHolder, String>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemListTopicsBinding
                = DataBindingUtil.inflate(inflater, R.layout.item_list_topics, parent, false)
        return TopicsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}