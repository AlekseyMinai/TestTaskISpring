package com.alesno.testtaskispring.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.model.repository.ApiRepository
import com.alesno.testtaskispring.model.service.ApiService
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {

    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        setUpViewPager()
        tab_layout.setupWithViewPager(view_pager)
        setupTabs()

        //redo it!
        val apiService = ApiService.create()
        val repository = ApiRepository(apiService)

        viewModel = ViewModelProviders
            .of(this, CommonViewModelFactory(repository))
            .get(CommonViewModel::class.java)

        viewModel.getResponseAsync()
    }


    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListAllMoviesFragment())
        adapter.addFragment(ListFavoritMoviesFragment())
        view_pager.adapter = adapter
    }

    private fun setupTabs(){
        setupTab(R.drawable.ic_movie_white_24dp, resources.getString(R.string.library), 0)
        setupTab(R.drawable.ic_favorite_white_24dp, resources.getString(R.string.favorite), 1)
    }

    private fun setupTab(iconResource: Int, text: String, position: Int){
        val tab: TextView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tab.text = text
        tab.setCompoundDrawablesWithIntrinsicBounds(0, iconResource, 0,0)
        tab_layout.getTabAt(position)?.customView = tab
    }
}
