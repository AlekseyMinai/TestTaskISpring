package com.alesno.testtaskispring.ui.listsactivity.videos.fragments

import android.os.Bundle
import android.view.View
import com.alesno.testtaskispring.ui.base.BaseListVideosFragment
import kotlinx.android.synthetic.main.fragment_list_movies.*

class ListAllMoviesFragment : BaseListVideosFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewListAllMoviesCreated()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    override fun refreshData() {
        viewModel.onRefreshedListAllVideos()
        swipe_to_refresh.isRefreshing = false
    }

    override fun isListFavorite(): Boolean {
        return false
    }

}