package com.alesno.testtaskispring.ui.listsactivity.videos.fragments

import com.alesno.testtaskispring.ui.base.BaseListVideosFragment
import kotlinx.android.synthetic.main.fragment_list_movies.*

class ListFavoriteMoviesFragment : BaseListVideosFragment() {

    override fun refreshData() {
        viewModel.onRefreshedListAllVideos()
        viewModel.setDataInFavoriteListFragment()
    }

    override fun isListFavorite(): Boolean {
        return true
    }
}