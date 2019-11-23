package com.alesno.testtaskispring.ui.videoactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.FragmentVideoBinding
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.ExpertsAdapter
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.TopicsAdapter

class VideoFragment: Fragment() {

    lateinit var binding: FragmentVideoBinding
    lateinit var viewModel: VideoViewModel
    lateinit var videoView: VideoView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        viewModel = VideoActivity.getVideoViewModel(activity!!)
        binding.viewModel = viewModel
        binding.handler = this

        setupUIElements()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
    }

    fun onPlayClicked(view: View, progressTime: Long){
        videoView.seekTo(progressTime.toInt())
        videoView.start()
        view.visibility = View.GONE
    }

    private fun setupUIElements(){
        setupRecyclerViewWithTopics()
        setupRecyclerViewWithExperts()
        setupVideoView()
    }

    private fun setupVideoView(){
        videoView = binding.videoView
        videoView.seekTo(1000)
    }

    private fun setupRecyclerViewWithTopics(){
        val recyclerView: RecyclerView = binding.recyclerViewTopics
        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TopicsAdapter()
    }

    private fun setupRecyclerViewWithExperts(){
        val recyclerView: RecyclerView = binding.recyclerViewExperts
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ExpertsAdapter()
    }

    override fun onPause() {
        super.onPause()
        videoView.stopPlayback()
    }

}