package com.alesno.testtaskispring.ui.videoactivity.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alesno.testtaskispring.databinding.ItemListExpertsBinding
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain.ExpertDetailDomain
import com.alesno.testtaskispring.ui.base.BaseRWAdapter

class ExpertsAdapter : BaseRWAdapter<ExpertsViewHolder, ExpertDetailDomain>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListExpertsBinding.inflate(inflater, parent, false)
        return ExpertsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpertsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}