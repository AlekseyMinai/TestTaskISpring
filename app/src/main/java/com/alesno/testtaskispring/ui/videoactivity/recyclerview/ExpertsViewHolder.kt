package com.alesno.testtaskispring.ui.videoactivity.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListExpertsBinding
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain.ExpertDetailDomain

class ExpertsViewHolder(val binding: ItemListExpertsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(expert: ExpertDetailDomain) {
        binding.expert = expert
        binding.executePendingBindings()
    }

}