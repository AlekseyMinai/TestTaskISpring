package com.alesno.testtaskispring.ui.videoactivity

import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.FragmentVideoBinding
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.ExpertsAdapter
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.TopicsAdapter
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : Fragment() {

    lateinit var binding: FragmentVideoBinding
    lateinit var viewModel: VideoViewModel
    lateinit var videoView: VideoView
    private var portraitSize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater, container)
        setupUIElements()
        setVideoReadyLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        portraitSize = getDefaultVideoSize()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onStopPlaybackVideo(videoView.currentPosition, videoView.duration)
        videoView.stopPlayback()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            changeVideoSize(getDisplayHeight())
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            changeVideoSize(portraitSize)
        }
    }

    private fun setVideoReadyLiveData() {
        viewModel.mPlayVideoLiveData.observe(this, Observer { time ->
            videoView.setOnPreparedListener {
                videoView.seekTo(time)
                videoView.start()
                viewModel.setVideoStat(false)
            }
        })
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        viewModel = VideoActivity.getVideoViewModel(activity!!)
        binding.viewModel = viewModel
        binding.handler = this
    }

    private fun setupUIElements() {
        setupRecyclerViewWithTopics()
        setupRecyclerViewWithExperts()
        setupVideoView()
    }

    private fun setupVideoView() {
        videoView = binding.videoView
        val mediaController = MediaController(activity)
        videoView.setMediaController(mediaController)
    }

    private fun setupRecyclerViewWithTopics() {
        val recyclerView: RecyclerView = binding.recyclerViewTopics
        recyclerView.layoutManager =
            LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TopicsAdapter()
    }

    private fun setupRecyclerViewWithExperts() {
        val recyclerView: RecyclerView = binding.recyclerViewExperts
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ExpertsAdapter()
    }

    private fun getDefaultVideoSize(): Int {
        val params: ViewGroup.LayoutParams? = frame_video_layout.layoutParams
        return params!!.height
    }

    private fun changeVideoSize(newSize: Int) {
        val params: ViewGroup.LayoutParams? = frame_video_layout.layoutParams
        params!!.height = newSize
        frame_video_layout.layoutParams = params
    }

    private fun getDisplayHeight(): Int {
        val display: Display = activity!!.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

}