package com.alesno.testtaskispring.ui.videoactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.FragmentVideoBinding
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.VideoAdapter

class VideoFragment: Fragment() {

    lateinit var binding: FragmentVideoBinding
    lateinit var viewModel: VideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        viewModel = VideoActivity.getVideoViewModel(activity!!)

        binding.viewModel = viewModel

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
    }

    private fun setupRecyclerView(){
        val recyclerView: RecyclerView = binding.recyclerViewTopics
        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = VideoAdapter()
    }
}