package com.alesno.testtaskispring.ui.listsactivity.videos.fragments

import android.os.Bundle
import android.view.View
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.ui.base.BaseListVideosFragment
import kotlinx.android.synthetic.main.fragment_list_movies.*

class ListAllMoviesFragment : BaseListVideosFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeToRefreshCallBack()
        viewModel.onViewListAllMoviesCreated()
    }

    private fun setupSwipeToRefreshCallBack() {
        swipe_to_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_to_refresh.setOnRefreshListener { swipe_to_refresh.isRefreshing = false }
    }

}